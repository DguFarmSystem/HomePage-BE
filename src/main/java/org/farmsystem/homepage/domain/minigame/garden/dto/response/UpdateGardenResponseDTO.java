package org.farmsystem.homepage.domain.minigame.garden.dto.response;

public record UpdateGardenResponseDTO(
        int x, // TODO: 어디에서는 Long이고 여기는 int네요.. 통일 부탁드립니다!!
        int y,
        Long tileType,
        UpdateGardenObjectResponseDTO object
) {
}
