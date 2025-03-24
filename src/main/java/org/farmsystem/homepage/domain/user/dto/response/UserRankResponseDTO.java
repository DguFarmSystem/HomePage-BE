package org.farmsystem.homepage.domain.user.dto.response;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.user.entity.User;

public record UserRankResponseDTO(
        Integer rank,
        long userId,
        String profileImageUrl,
        String name,
        Integer generation,
        Track track,
        Integer totalSeed
) {
    public static UserRankResponseDTO from(Integer rank, User user) {
        return new UserRankResponseDTO(
                rank,
                user.getUserId(),
                user.getProfileImageUrl(),
                user.getName(),
                user.getGeneration(),
                user.getTrack(),
                user.getTotalSeed()
        );
    }
}
