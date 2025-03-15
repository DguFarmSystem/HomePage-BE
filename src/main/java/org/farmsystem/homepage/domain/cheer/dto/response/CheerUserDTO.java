package org.farmsystem.homepage.domain.cheer.dto.response;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.user.entity.User;

public record CheerUserDTO(
        String name,
        String profileImageUrl,
        Integer generation,
        Track track
) {
    public static CheerUserDTO from(User user) {
        return new CheerUserDTO(
                user.getName(),
                user.getProfileImageUrl(),
                user.getGeneration(),
                user.getTrack()
        );
    }
}
