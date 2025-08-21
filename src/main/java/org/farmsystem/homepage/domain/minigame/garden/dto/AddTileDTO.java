package org.farmsystem.homepage.domain.minigame.garden.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.farmsystem.homepage.domain.minigame.garden.entity.GardenTile;

public record AddTileDTO(
        Long x,
        Long y,
        @JsonProperty("tile_type") Integer tileType

        ){
    public static AddTileDTO from(GardenTile gardenTile) {
        return new AddTileDTO(
                gardenTile.getX(),
                gardenTile.getY(),
                gardenTile.getTileType()
        );
    }
}
