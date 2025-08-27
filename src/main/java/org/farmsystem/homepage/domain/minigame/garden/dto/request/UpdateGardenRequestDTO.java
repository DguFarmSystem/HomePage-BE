package org.farmsystem.homepage.domain.minigame.garden.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UpdateGardenRequestDTO (
        @NotNull
        @JsonProperty(value = "tileType", required = true)
        Long tileType,

        @Valid UpdateGardenObjectRequestDTO object
){
}
