package org.farmsystem.homepage.domain.minigame.garden.dto.response;

import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;

public record UpdateGardenObjectResponseDTO(
        Long objectType,  //store_goods_number
        Rotation rotation
) {
}
