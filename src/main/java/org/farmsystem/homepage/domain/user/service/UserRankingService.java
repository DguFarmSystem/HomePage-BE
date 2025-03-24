package org.farmsystem.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.farmsystem.homepage.domain.user.dto.response.UserRankListResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserRankResponseDTO;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.farmsystem.homepage.global.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRankingService {

    private final UserRepository userRepository;
    private final RankingCacheService rankingCacheService;


    // 랭킹 조회
    public UserRankListResponseDTO getDailyRanking(Long userId) {

        UserRankResponseDTO myRank = getMyRank(userId);
        List<UserRankResponseDTO> rankList = rankingCacheService.getRankingList();

        return new UserRankListResponseDTO(myRank, rankList);
    }

    // 본인 랭킹
    private UserRankResponseDTO getMyRank(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        return rankingCacheService.getRankingList().stream()
                .filter(userRankResponseDTO -> userRankResponseDTO.userId() == user.getUserId())
                .findFirst()
                .orElse(null);
    }
}

