package org.farmsystem.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.farmsystem.homepage.domain.user.dto.response.UserRankResponseDTO;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingCacheService {

    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    // 랭킹 조회
    @Cacheable(value = "dailySeedRanking", key = "'all'")
    public List<UserRankResponseDTO> getRankingList() {
        List<User> rankedUsers = userRepository.findAllByOrderByTotalSeedDesc();

        List<UserRankResponseDTO> rankList = new ArrayList<>();
        int rank = 1, prevSeed = -1, actualRank = 1;

        for (User user : rankedUsers) {
            if (user.getTotalSeed() != prevSeed) {
                actualRank = rank;
            }
            rankList.add(UserRankResponseDTO.from(actualRank, user));;

            prevSeed = user.getTotalSeed();
            rank++;
        }
        return rankList;
    }

    // 자정마다 랭킹 업데이트
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    @CachePut(value = "dailySeedRanking", key = "'all'")
    public List<UserRankResponseDTO> updateDailyRanking() {

        log.info("씨앗 랭킹 업데이트 스케줄링 : {}", LocalDate.now());

        cacheManager.getCache("dailySeedRanking").evict("all");
        return getRankingList();
    }

}
