package org.farmsystem.homepage.domain.farmingLog.dto;

import org.farmsystem.homepage.domain.farmingLog.entity.FarmingLog;
import org.farmsystem.homepage.domain.user.entity.User;

public record FarmingLogResponseDTO(
        Long farmingLogId,
        String title,
        String content,
        String category,
        String createdAt,
        String author,
        String profileImageUrl,
        String track,
        Integer generation,
        boolean isOwner,
        boolean isLiked,
        long likeCount
) {
    public static FarmingLogResponseDTO from(FarmingLog farmingLog, Long currentUserId, boolean isLiked, long likeCount) {
        User user = farmingLog.getUser();

        return new FarmingLogResponseDTO(
                farmingLog.getFarmingLogId(),
                farmingLog.getTitle(),
                farmingLog.getContent(),
                farmingLog.getCategory().name(),
                farmingLog.getCreatedAt().toString(),
                user.getName(),
                user.getProfileImageUrl(),
                user.getTrack() != null ? user.getTrack().name() : null,
                user.getGeneration(),
                user.getUserId().equals(currentUserId),
                isLiked,
                likeCount
        );
    }
}
