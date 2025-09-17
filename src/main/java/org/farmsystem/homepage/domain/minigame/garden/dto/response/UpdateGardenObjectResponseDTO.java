package org.farmsystem.homepage.domain.minigame.garden.dto.response;

import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;

public record UpdateGardenObjectResponseDTO(
        Long objectType,  //store_goods_number // TODO: 자세한 주석 요청드립니다! + Long이유..?
        Rotation rotation
) {
}
