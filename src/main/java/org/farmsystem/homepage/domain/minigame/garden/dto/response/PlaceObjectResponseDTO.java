package org.farmsystem.homepage.domain.minigame.garden.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;

public record PlaceObjectResponseDTO(
        Long x,
        Long y,
        @JsonProperty("object_type") Long objectType,
        Rotation rotation
) {
}
