package org.farmsystem.homepage.domain.minigame.garden.dto.request;

import jakarta.validation.constraints.NotNull;
import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;

public record UpdateGardenObjectRequestDTO(
        @NotNull Long objectType,  //store_goods_number // TODO: 이게 뭔지 자세한 주석 요청드립니다~!
        @NotNull Rotation rotation
) {
}
