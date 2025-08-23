package org.farmsystem.homepage.domain.minigame.player.dto.response;

import org.farmsystem.homepage.domain.minigame.player.entity.Badge;

public record BadgeResponseDTO(
        Long badgeId,
        Integer badgeType
) {
    public static BadgeResponseDTO from(Badge badge) {
        return new BadgeResponseDTO(badge.getBadgeId(), badge.getBadgeType());
    }
}
