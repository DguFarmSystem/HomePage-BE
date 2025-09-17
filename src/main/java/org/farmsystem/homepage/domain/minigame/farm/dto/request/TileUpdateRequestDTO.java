package org.farmsystem.homepage.domain.minigame.farm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TileUpdateRequestDTO(
        int x,
        int y,
        @NotBlank String status, // empty, planted, ready
        LocalDateTime plantedAt,
        @NotNull Integer sunlightCount
) {
}
