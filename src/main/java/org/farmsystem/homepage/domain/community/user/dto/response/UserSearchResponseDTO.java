package org.farmsystem.homepage.domain.community.user.dto.response;

import lombok.Builder;
import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.community.user.entity.User;

@Builder
public record UserSearchResponseDTO(
        Long userId,
        String name,
        String profileImageUrl,
        Track track,
        Integer generation
) {
    public static UserSearchResponseDTO from(User user) {
        return UserSearchResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .profileImageUrl(user.getProfileImageUrl())
                .track(user.getTrack())
                .generation(user.getGeneration())
                .build();
    }
}