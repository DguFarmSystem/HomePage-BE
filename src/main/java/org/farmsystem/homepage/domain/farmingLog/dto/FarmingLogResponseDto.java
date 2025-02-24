package org.farmsystem.homepage.domain.farmingLog.dto;

import org.farmsystem.homepage.domain.farmingLog.entity.FarmingLog;

public record FarmingLogResponseDto(
        Long farmingLogId,
        String title,
        String content,
        String category,
        String createdAt,
        String updatedAt,
        String author,
        boolean isOwner,
        boolean isLiked,
        long likeCount
) {
    public static FarmingLogResponseDto from(FarmingLog farmingLog, Long currentUserId, boolean isLiked, long likeCount) {
        return new FarmingLogResponseDto(
                farmingLog.getFarmingLogId(),
                farmingLog.getTitle(),
                farmingLog.getContent(),
                farmingLog.getCategory().name(),
                farmingLog.getCreatedAt().toString(),
                farmingLog.getUpdatedAt().toString(),
                farmingLog.getUser().getName(),
                farmingLog.getUser().getUserId().equals(currentUserId),
                isLiked,
                likeCount
        );
    }
}
