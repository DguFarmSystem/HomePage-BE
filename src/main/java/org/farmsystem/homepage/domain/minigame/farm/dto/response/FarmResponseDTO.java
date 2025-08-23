package org.farmsystem.homepage.domain.minigame.farm.dto.response;

import java.util.List;


public record FarmResponseDTO(
        List<TileResponseDTO> tiles
) {
}
