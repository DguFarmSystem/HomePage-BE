package org.farmsystem.homepage.domain.minigame.farm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.farm.dto.request.TileUpdateRequest;
import org.farmsystem.homepage.domain.minigame.farm.dto.response.FarmResponse;
import org.farmsystem.homepage.domain.minigame.farm.dto.response.TileResponse;
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
    public FarmResponse getFarm(Long userId) {
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

        List<TileResponse> tileResponses = new ArrayList<>();
        for (FarmplotTile tile : tiles) {
            PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);
            int[] xy = toXY(tile.getTileNum());
            tileResponses.add(new TileResponse(
                    xy[0],
                    xy[1],
                    plant == null ? "empty" : plant.getStatus().name().toLowerCase(),
                    plant == null ? null : plant.getPlantedAt(),
                    plant == null ? 0 : plant.getSunlightCount()
            ));
        }

        return new FarmResponse(tileResponses);
    }

    @Transactional
    public TileResponse getTile(Long userId, int x, int y) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        int tileNum = toTileNum(x, y);
        FarmplotTile tile = tileRepository.findByPlayerAndTileNum(player, tileNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.FARMTILE_NOT_FOUND));

        PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);

        return new TileResponse(
                x,
                y,
                plant == null ? "empty" : plant.getStatus().name().toLowerCase(),
                plant == null ? null : plant.getPlantedAt(),
                plant == null ? 0 : plant.getSunlightCount()
        );
    }

    @Transactional
    public TileResponse updateTile(Long userId, TileUpdateRequest request) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        int tileNum = toTileNum(request.x(), request.y());
        FarmplotTile tile = tileRepository.findByPlayerAndTileNum(player, tileNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.FARMTILE_NOT_FOUND));

        PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);
        String newStatusStr = request.status().toLowerCase();

        // empty로 변경 요청 → 초기화
        if ("empty".equals(newStatusStr)) {
            if (plant != null) {
                plantRepository.delete(plant);
            }
            return new TileResponse(
                    request.x(),
                    request.y(),
                    "empty",
                    null,
                    0
            );
        }

        // 그 외 모든 상태 → 생성 or 업데이트
        PlantStatus newStatus;
        try {
            newStatus = PlantStatus.valueOf(request.status().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        // planted 또는 ready 상태에서는 plantedAt, sunlightCount 필수
        if (newStatus == PlantStatus.PLANTED || newStatus == PlantStatus.READY) {
            if (request.plantedAt() == null || request.sunlightCount() == null) {
                throw new BusinessException(ErrorCode.MISSING_REQUIRED_FIELD);
            }
        }

        if (plant == null) {
            plant = PlantedPlant.builder()
                    .farmplotTile(tile)
                    .player(player)
                    .plantedAt(request.plantedAt())
                    .status(newStatus)
                    .sunlightCount(request.sunlightCount())
                    .build();
            plantRepository.save(plant);
        } else {
            plant.setStatus(newStatus);
            plant.setSunlightCount(request.sunlightCount());
            plant.setPlantedAt(request.plantedAt());
        }

        return new TileResponse(
                request.x(),
                request.y(),
                plant.getStatus().name().toLowerCase(),
                plant.getPlantedAt(),
                plant.getSunlightCount()
        );
    }
}
