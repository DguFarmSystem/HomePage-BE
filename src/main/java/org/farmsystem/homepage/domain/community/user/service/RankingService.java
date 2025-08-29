package org.farmsystem.homepage.domain.community.user.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.community.user.dto.response.UserRankListResponseDTO;
import org.farmsystem.homepage.domain.community.user.dto.response.UserRankResponseDTO;
import org.farmsystem.homepage.domain.community.user.entity.User;
import org.farmsystem.homepage.domain.community.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.farmsystem.homepage.global.error.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingService {

    private final StringRedisTemplate redisTemplate;
    private final UserRepository userRepository;
    private static final String RANKING_KEY = "user-ranking";

    // DB 에서 유저 정보 가져와서 Redis 랭킹 초기화
    @Transactional
    @Scheduled(cron = "0 0 0 * * *") // (DB & Redis 동기화)
    public void initializeRanking() {
        List<User> users = userRepository.findAll();
        redisTemplate.opsForZSet().removeRange(RANKING_KEY, 0, -1);

        for (User user : users) {
            redisTemplate.opsForZSet().add(RANKING_KEY, user.getUserId().toString(), user.getTotalSeed());
        }
    }

    // 랭킹 조회 (내 랭킹 + 전체 랭킹)
    public UserRankListResponseDTO getDailyRanking(Long userId) {

        // 랭킹 데이터 조회
        List<UserRankResponseDTO> rankList = getAllRankings();
        UserRankResponseDTO myRank = getMyRank(userId, rankList);

        // 내 랭킹이 없으면 초기화 후 다시 조회 (유저 새로 가입한 경우)
        if (myRank == null) {
            initializeRanking();
            rankList = getAllRankings();
            myRank = getMyRank(userId, rankList);
        }

        return new UserRankListResponseDTO(myRank, rankList);
    }

    // 전체 랭킹
    private List<UserRankResponseDTO> getAllRankings() {
        // Redis에서 랭킹 정보를 {userId: score} 형태로 가져옴
        Set<ZSetOperations.TypedTuple<String>> rankings = redisTemplate.opsForZSet().reverseRangeWithScores(RANKING_KEY, 0, -1);

        List<UserRankResponseDTO> rankList = new ArrayList<>();
        double previousScore = -1;
        int rank = 1;

        // ZSet 기존 정렬 유지하면서 동점자 처리
        for (ZSetOperations.TypedTuple<String> entry : rankings) {
            Long userId = Long.parseLong(entry.getValue());
            double score = entry.getScore();

            if (score != previousScore) {
                rank = rankList.size() + 1;
            }

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

            rankList.add(UserRankResponseDTO.from(rank, user));
            previousScore = score;
        }

        return rankList;
    }

    // 내 랭킹
    private UserRankResponseDTO getMyRank(Long userId, List<UserRankResponseDTO> rankList) {
        return rankList.stream()
                .filter(userRank -> userRank.userId() == userId)
                .findFirst()
                .orElse(null);
    }
}
