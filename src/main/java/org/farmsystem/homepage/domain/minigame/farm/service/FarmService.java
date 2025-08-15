package org.farmsystem.homepage.domain.minigame.farm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.farm.dto.FarmDTO;
import org.farmsystem.homepage.domain.minigame.farm.entity.FarmplotTile;
import org.farmsystem.homepage.domain.minigame.farm.entity.PlantedPlant;
import org.farmsystem.homepage.domain.minigame.farm.entity.PlantStatus;
import org.farmsystem.homepage.domain.minigame.farm.repository.FarmplotTileRepository;
import org.farmsystem.homepage.domain.minigame.farm.repository.PlantedPlantRepository;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.player.repository.PlayerRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmService {

    private final PlayerRepository playerRepository;
    private final FarmplotTileRepository tileRepository;
    private final PlantedPlantRepository plantRepository;

    private int toTileNum(int x, int y) {
        return y * 3 + (x + 1); // 3x3, 좌하단 (0,0) 시작
    }

    private int[] toXY(int tileNum) {
        int y = (tileNum - 1) / 3;
        int x = (tileNum - 1) % 3;
        return new int[]{x, y};
    }

    @Transactional
    public FarmDTO.FarmResponse getFarm(Long userId) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        List<FarmplotTile> tiles = tileRepository.findByPlayer(player);
        if (tiles.isEmpty()) {
            for (int i = 1; i <= 9; i++) {
                tileRepository.save(FarmplotTile.builder()
                        .tileNum(i)
                        .player(player)
                        .build());
            }
            tiles = tileRepository.findByPlayer(player);
        }

        List<FarmDTO.TileResponse> tileResponses = new ArrayList<>();
        for (FarmplotTile tile : tiles) {
            PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);
            int[] xy = toXY(tile.getTileNum());
            tileResponses.add(FarmDTO.TileResponse.builder()
                    .x(xy[0])
                    .y(xy[1])
                    .status(plant == null ? "empty" : plant.getStatus().name().toLowerCase())
                    .plantedAt(plant == null ? null : plant.getPlantedAt())
                    .sunlightCount(plant == null ? 0 : plant.getSunlightCount())
                    .build());
        }

        return FarmDTO.FarmResponse.builder()
                .tiles(tileResponses)
                .build();
    }

    @Transactional
    public FarmDTO.TileResponse getTile(Long userId, int x, int y) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        int tileNum = toTileNum(x, y);
        FarmplotTile tile = tileRepository.findByPlayerAndTileNum(player, tileNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.GARDENTILE_NOT_FOUND));

        PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);

        return FarmDTO.TileResponse.builder()
                .x(x)
                .y(y)
                .status(plant == null ? "empty" : plant.getStatus().name().toLowerCase())
                .plantedAt(plant == null ? null : plant.getPlantedAt())
                .sunlightCount(plant == null ? 0 : plant.getSunlightCount())
                .build();
    }

    @Transactional
    public FarmDTO.TileResponse updateTile(Long userId, FarmDTO.TileUpdateRequest request) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        int tileNum = toTileNum(request.getX(), request.getY());
        FarmplotTile tile = tileRepository.findByPlayerAndTileNum(player, tileNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.GARDENTILE_NOT_FOUND));

        PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);

        String newStatusStr = request.getStatus().toLowerCase();

        // empty로 변경 요청 → 초기화
        if ("empty".equals(newStatusStr)) {
            if (plant != null) {
                plantRepository.delete(plant);
            }
            return FarmDTO.TileResponse.builder()
                    .x(request.getX())
                    .y(request.getY())
                    .status("empty")
                    .plantedAt(null)
                    .sunlightCount(0)
                    .build();
        }

        // 그 외 모든 상태 → 생성 or 업데이트
        PlantStatus newStatus;
        try {
            newStatus = PlantStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        if (plant == null) {
            plant = PlantedPlant.builder()
                    .farmplotTile(tile)
                    .player(player)
                    .plantedAt(request.getPlantedAt()) // 유니티에서 받은 시간
                    .status(newStatus)
                    .sunlightCount(request.getSunlightCount())
                    .build();
            plantRepository.save(plant);
        } else {
            plant.setStatus(newStatus);
            plant.setSunlightCount(request.getSunlightCount());
            plant.setPlantedAt(request.getPlantedAt());
        }

        return FarmDTO.TileResponse.builder()
                .x(request.getX())
                .y(request.getY())
                .status(plant.getStatus().name().toLowerCase())
                .plantedAt(plant.getPlantedAt())
                .sunlightCount(plant.getSunlightCount())
                .build();
    }
}
