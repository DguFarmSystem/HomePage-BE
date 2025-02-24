package org.farmsystem.homepage.domain.farmingLog.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.farmingLog.entity.FarmingLog;
import org.farmsystem.homepage.domain.farmingLog.entity.FarmingLogLike;
import org.farmsystem.homepage.domain.farmingLog.repository.FarmingLogLikeRepository;
import org.farmsystem.homepage.domain.farmingLog.repository.FarmingLogRepository;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FarmingLogLikeService {
    private final FarmingLogRepository farmingLogRepository;
    private final FarmingLogLikeRepository farmingLogLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void toggleLike(Long userId, Long farmingLogId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        FarmingLog farmingLog = farmingLogRepository.findById(farmingLogId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FARMING_LOG_NOT_FOUNT));

        farmingLogLikeRepository.findByUserAndFarmingLog(user, farmingLog)
                .ifPresentOrElse(
                        farmingLogLikeRepository::delete,
                        () -> farmingLogLikeRepository.save(
                                FarmingLogLike.builder()
                                        .user(user)
                                        .farmingLog(farmingLog)
                                        .build()
                        )
                );
    }
}
