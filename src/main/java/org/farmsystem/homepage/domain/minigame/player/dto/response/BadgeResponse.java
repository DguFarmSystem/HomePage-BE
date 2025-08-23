package org.farmsystem.homepage.domain.minigame.player.dto.response;

import org.farmsystem.homepage.domain.minigame.player.entity.Badge;

public record BadgeResponse(
        Long badgeId,
        Integer badgeType
) {
    public static BadgeResponse from(Badge badge) {
        return new BadgeResponse(badge.getBadgeId(), badge.getBadgeType());
    }
}
