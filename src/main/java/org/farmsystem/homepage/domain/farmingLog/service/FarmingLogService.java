package org.farmsystem.homepage.domain.farmingLog.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.farmingLog.dto.FarmingLogRequestDTO;
import org.farmsystem.homepage.domain.farmingLog.dto.FarmingLogResponseDTO;
import org.farmsystem.homepage.domain.farmingLog.entity.FarmingLog;
import org.farmsystem.homepage.domain.farmingLog.repository.FarmingLogLikeRepository;
import org.farmsystem.homepage.domain.farmingLog.repository.FarmingLogRepository;
import org.farmsystem.homepage.domain.user.entity.SeedEventType;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.domain.user.service.DailySeedService;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FarmingLogService {

    private final FarmingLogRepository farmingLogRepository;
    private final FarmingLogLikeRepository farmingLogLikeRepository;
    private final UserRepository userRepository;
    private final DailySeedService dailySeedService;

    private FarmingLogResponseDTO mapToFarmingLogResponse(FarmingLog farmingLog, User currentUser) {
        boolean isLiked = farmingLogLikeRepository.existsByUserAndFarmingLogAndIsDeletedFalse(currentUser, farmingLog);
        long likeCount = farmingLogLikeRepository.countByFarmingLogAndIsDeletedFalse(farmingLog);

        return FarmingLogResponseDTO.from(farmingLog, currentUser.getUserId(), isLiked, likeCount);
    }

    @Transactional(readOnly = true)
    public Page<FarmingLogResponseDTO> getAllFarmingLogs(Long userId, Pageable pageable) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return farmingLogRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(farmingLog -> mapToFarmingLogResponse(farmingLog, currentUser));
    }

    @Transactional(readOnly = true)
    public Page<FarmingLogResponseDTO> getMyFarmingLogs(Long userId, Pageable pageable) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return farmingLogRepository.findByUserOrderByCreatedAtDesc(currentUser, pageable)
                .map(farmingLog -> mapToFarmingLogResponse(farmingLog, currentUser));
    }

    @Transactional
    public FarmingLogResponseDTO createFarmingLog(Long userId, FarmingLogRequestDTO requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        FarmingLog farmingLog = FarmingLog.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .category(requestDto.category())
                .user(user)
                .build();

        farmingLogRepository.save(farmingLog);

        dailySeedService.earnSeed(userId, SeedEventType.FARMING_LOG);
        return FarmingLogResponseDTO.from(farmingLog, userId, false, 0);
    }

    @Transactional
    public FarmingLogResponseDTO updateFarmingLog(Long userId, Long farmingLogId, FarmingLogRequestDTO requestDto) {
        FarmingLog farmingLog = farmingLogRepository.findById(farmingLogId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FARMING_LOG_NOT_FOUND));

        if (!farmingLog.getUser().getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        farmingLog.update(requestDto.title(), requestDto.content(), requestDto.category());
        return FarmingLogResponseDTO.from(farmingLog, userId, false, 0);
    }

    @Transactional
    public void deleteFarmingLog(Long userId, Long farmingLogId) {
        FarmingLog farmingLog = farmingLogRepository.findById(farmingLogId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FARMING_LOG_NOT_FOUND));

        if (!farmingLog.getUser().getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        farmingLogRepository.delete(farmingLog);
    }
}
