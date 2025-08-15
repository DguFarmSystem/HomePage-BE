package org.farmsystem.homepage.domain.minigame.garden.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MoveObjectRequestDTO(
        @JsonProperty("from_x") Long fromX,
        @JsonProperty("from_y") Long fromY,
        @JsonProperty("object_type") Long objectType,
        @JsonProperty("to_x") Long toX,
        @JsonProperty("to_y") Long toY
) {
}
