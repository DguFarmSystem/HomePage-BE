package org.farmsystem.homepage.domain.minigame.garden.dto.response;

import org.farmsystem.homepage.domain.minigame.garden.dto.request.UpdateGardenObjectRequestDTO;

public record UpdateGardenResponseDTO(
        int x,
        int y,
        Long tileType,
        UpdateGardenObjectResponseDTO object
) {
}
