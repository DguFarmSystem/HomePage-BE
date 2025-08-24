package org.farmsystem.homepage.domain.minigame.garden.dto.request;

public record UpdateGardenRequestDTO (
        Long tileType,
        UpdateGardenObjectRequestDTO object
){
}
