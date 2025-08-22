package org.farmsystem.homepage.domain.minigame.farm.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TileUpdateRequest(
        int x,
        int y,
        @NotBlank String status, // empty, planted, ready
        LocalDateTime plantedAt,
        @NotNull Integer sunlightCount
) {
}
