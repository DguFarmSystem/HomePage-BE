package org.farmsystem.homepage.domain.minigame.garden.dto.request;

import jakarta.validation.constraints.NotNull;
import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;

public record UpdateGardenObjectRequestDTO(
        @NotNull Long objectType,  //store_goods_number
        @NotNull Rotation rotation
) {
}
