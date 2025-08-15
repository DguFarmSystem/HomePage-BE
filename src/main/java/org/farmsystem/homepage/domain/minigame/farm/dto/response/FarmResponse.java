package org.farmsystem.homepage.domain.minigame.farm.dto.response;

import java.util.List;

public record FarmResponse(
        List<TileResponse> tiles
) {
}
