package org.farmsystem.homepage.domain.minigame.farm.dto.response;

import org.farmsystem.homepage.domain.minigame.farm.entity.FarmplotTile;
import org.farmsystem.homepage.domain.minigame.farm.entity.PlantedPlant;

import java.time.LocalDateTime;

public record TileResponse(
        int x,
        int y,
        String status,
        LocalDateTime plantedAt,
        Integer sunlightCount
) {
    public static TileResponse from(FarmplotTile tile, PlantedPlant plant) {
        int[] xy = toXY(tile.getTileNum());
        return new TileResponse(
                xy[0],
                xy[1],
                plant == null ? "empty" : plant.getStatus().name().toLowerCase(),
                plant == null ? null : plant.getPlantedAt(),
                plant == null ? 0 : plant.getSunlightCount()
        );
    }

    private static int[] toXY(int tileNum) {
        int y = (tileNum - 1) / 3;
        int x = (tileNum - 1) % 3;
        return new int[]{x, y};
    }
}
