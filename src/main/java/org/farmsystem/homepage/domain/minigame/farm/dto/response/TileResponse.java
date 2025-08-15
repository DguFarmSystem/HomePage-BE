package org.farmsystem.homepage.domain.minigame.farm.dto.response;

import java.time.LocalDateTime;

public record TileResponse(
        int x,
        int y,
        String status,
        LocalDateTime plantedAt,
        Integer sunlightCount
) {
}
