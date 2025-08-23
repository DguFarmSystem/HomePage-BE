package org.farmsystem.homepage.domain.minigame.farm.dto.response;

import org.farmsystem.homepage.domain.minigame.farm.entity.FarmplotTile;
import org.farmsystem.homepage.domain.minigame.farm.entity.PlantedPlant;

import java.time.LocalDateTime;

public record TileResponseDTO(
        int x,
        int y,
        String status,
        LocalDateTime plantedAt,
        Integer sunlightCount
) {
    // 타일/식물 정보를 DTO로 변환
    public static TileResponseDTO from(FarmplotTile tile, PlantedPlant plant) {
        int[] xy = toXY(tile.getTileNum());
        return new TileResponseDTO(
                xy[0],
                xy[1],
                plant == null ? "empty" : plant.getStatus().name().toLowerCase(),
                plant == null ? null : plant.getPlantedAt(),
                plant == null ? 0 : plant.getSunlightCount()
        );
    }

    // 타일 번호(1~9)를 3x3 좌표계 (x, y)로 변환
    private static int[] toXY(int tileNum) {
        int y = (tileNum - 1) / 3;
        int x = (tileNum - 1) % 3;
        return new int[]{x, y};
    }
}
