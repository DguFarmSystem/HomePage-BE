package org.farmsystem.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.farmsystem.homepage.domain.user.dto.response.TodaySeedResponseDTO;
import org.farmsystem.homepage.domain.user.entity.DailySeed;
import org.farmsystem.homepage.domain.user.entity.SeedEventType;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.DailySeedRepository;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.farmsystem.homepage.global.error.ErrorCode.ALREADY_ATTENDANCE;
import static org.farmsystem.homepage.global.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailySeedService {

    private final DailySeedRepository dailySeedRepository;
    private final UserRepository userRepository;

    //TODO : 적립 필요한 이벤트에 earnSeed 함수 호출 추가하기
    // 예 : dailySeedService.earnSeed(userId, SeedEventType.ATTENDANCE);
    @Transactional
    public void earnSeed(Long userId, SeedEventType eventType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        DailySeed dailySeed = dailySeedRepository.findByUser(user)
                .orElseGet(() -> new DailySeed(user));

        handleSeedEvent(dailySeed, eventType, user);

        dailySeedRepository.save(dailySeed);
        userRepository.save(user);
    }

    // 중복 적립 방지
    private void handleSeedEvent(DailySeed dailySeed, SeedEventType eventType, User user) {
        switch (eventType) {
            case ATTENDANCE:
                // 중복 출석 방지
                if (dailySeed.isAttendance()) {
                    throw new BusinessException(ALREADY_ATTENDANCE);
                }
                dailySeed.updateAttendance();
                user.addTotalSeed(eventType.getSeedAmount());
                break;

            case CHEER:
                if (dailySeed.isCheer()) return;
                dailySeed.updateCheer();
                user.addTotalSeed(eventType.getSeedAmount());
                break;
            case FARMING_LOG:
                if (dailySeed.isFarminglog()) return;
                dailySeed.updateFarminglog();
                user.addTotalSeed(eventType.getSeedAmount());
                break;
        }
    }

    public TodaySeedResponseDTO getTodaySeed(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        DailySeed dailySeed = dailySeedRepository.findByUser(user)
                .orElseGet(() -> new DailySeed(user));

        return TodaySeedResponseDTO.from(dailySeed);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void resetDailySeed() {
        log.info("씨앗 적립 초기화 스케줄링 실행 : {}", LocalDate.now());
        dailySeedRepository.findAll().forEach(dailySeed -> {
            dailySeed.reset();
        });
    }
}
