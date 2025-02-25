package org.farmsystem.homepage.domain.user.dto.response;

import lombok.Builder;
import org.farmsystem.homepage.domain.common.entity.Track;

@Builder
public record UserSearchResponseDTO(
        Long userId,
        String name,
        String profileImageUrl,
        Track track,
        Integer generation
) {
}