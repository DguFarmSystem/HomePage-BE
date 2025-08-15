package org.farmsystem.homepage.domain.minigame.farm.dto.request;

import java.time.LocalDateTime;

public record TileUpdateRequest(
        int x,
        int y,
        String status, // empty, planted, ready
        LocalDateTime plantedAt,
        Integer sunlightCount
) {
}
