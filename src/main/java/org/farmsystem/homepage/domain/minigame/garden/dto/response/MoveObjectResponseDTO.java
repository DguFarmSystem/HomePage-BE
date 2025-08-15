package org.farmsystem.homepage.domain.minigame.garden.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MoveObjectResponseDTO(
        @JsonProperty("to_x") Long toX,
        @JsonProperty("to_y") Long toY,
        @JsonProperty("object_type") Long objectType
) {
}
