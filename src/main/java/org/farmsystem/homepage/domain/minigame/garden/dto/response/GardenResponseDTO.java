package org.farmsystem.homepage.domain.minigame.garden.dto.response;

import org.farmsystem.homepage.domain.minigame.garden.entity.GardenTile;

public record GardenResponseDTO (
        Long tileId,
        Long x,
        Long y,
        Long tileType,
        PlacedObjectResponseDTO object
){
    public static GardenResponseDTO from(GardenTile tile, PlacedObjectResponseDTO object) {
        return new GardenResponseDTO(
                tile.getTileId(),
                tile.getX(),
                tile.getY(),
                tile.getTileType().getStoreGoodsNumber(),
                object
        );
    }
}
