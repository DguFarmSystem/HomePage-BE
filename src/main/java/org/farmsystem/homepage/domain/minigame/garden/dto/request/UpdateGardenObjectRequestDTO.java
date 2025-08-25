package org.farmsystem.homepage.domain.minigame.garden.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;

public record UpdateGardenObjectRequestDTO(
        Long objectType,  //store_goods_number
        Rotation rotation
) {
}
