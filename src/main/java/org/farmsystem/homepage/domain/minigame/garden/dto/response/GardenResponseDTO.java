package org.farmsystem.homepage.domain.minigame.garden.dto.response;

import org.farmsystem.homepage.domain.minigame.garden.entity.GardenTile;

public record GardenResponseDTO (
        Long tileId,
        Long x, // TODO: 이것도 Long인 이유가 있을까요? (tileType 도)
        Long y,
        Long tileType,
        PlacedObjectResponseDTO object
){
    public static GardenResponseDTO from(GardenTile tile, PlacedObjectResponseDTO object) {
        return new GardenResponseDTO(
                tile.getGardenTileId(),
                tile.getX(),
                tile.getY(),
                tile.getTileType().getGoodsNumber(),
                object
        );
    }
}
