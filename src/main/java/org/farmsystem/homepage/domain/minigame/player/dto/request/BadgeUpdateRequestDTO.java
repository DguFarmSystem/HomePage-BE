package org.farmsystem.homepage.domain.minigame.player.dto.request;

import jakarta.validation.constraints.NotNull;

public record BadgeUpdateRequestDTO(
        @NotNull Integer badgeType
) {
}
