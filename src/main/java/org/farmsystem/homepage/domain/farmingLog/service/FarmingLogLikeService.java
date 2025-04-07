package org.farmsystem.homepage.domain.farmingLog.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.farmingLog.entity.FarmingLog;
import org.farmsystem.homepage.domain.farmingLog.entity.FarmingLogLike;
import org.farmsystem.homepage.domain.farmingLog.repository.FarmingLogLikeRepository;
import org.farmsystem.homepage.domain.farmingLog.repository.FarmingLogRepository;
import org.farmsystem.homepage.domain.notification.event.FarmingLogLikedEvent;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FarmingLogLikeService {
    private final FarmingLogRepository farmingLogRepository;
    private final FarmingLogLikeRepository farmingLogLikeRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void toggleLike(Long userId, Long farmingLogId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        FarmingLog farmingLog = farmingLogRepository.findById(farmingLogId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FARMING_LOG_NOT_FOUND));
        Optional<FarmingLogLike> existingLike = farmingLogLikeRepository.findByUserAndFarmingLogAndIsDeletedFalse(user, farmingLog);
        if (existingLike.isPresent()) {
            isLikeAdded = false;
            existingLike.get().updateDelete();
        } else {
            farmingLogLikeRepository.save(
                    FarmingLogLike.builder()
                            .user(user)
                            .farmingLog(farmingLog)
                            .build()
            );
            isLikeAdded = true;
        }
        // 좋아요가 추가된 경우에만 이벤트 전송
        if (isLikeAdded) {
            FarmingLogLikedEvent event = new FarmingLogLikedEvent(
                    farmingLog.getUser().getUserId(),   // 알림을 받을 사용자(파밍로그 작성자) 번호
                    user.getGeneration(),               // 좋아요를 한 사용자 기수
                    user.getTrack(),                    // 좋아요를 한 사용자 트랙
                    user.getName(),                     // 좋아요를 한 사용자 이름
                    farmingLog.getTitle(),              // 관련된 파밍로그 제목
                    farmingLog.getFarmingLogId()        // 관련된 파밍로그 번호
            );
            applicationEventPublisher.publishEvent(event);
        }
    }
}
