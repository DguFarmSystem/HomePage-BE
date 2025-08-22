package org.farmsystem.homepage.domain.minigame.farm.service;

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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class FarmService {

    private final PlayerRepository playerRepository;
    private final FarmplotTileRepository tileRepository;
    private final PlantedPlantRepository plantRepository;

    private Player findPlayerOrThrow(Long userId) {
        return playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));
    }

    private int toTileNum(int x, int y) {
        return y * 3 + (x + 1);
    }

    @Transactional(readOnly = true)
    public FarmResponse getFarm(Long userId) {
        Player player = findPlayerOrThrow(userId);

        List<FarmplotTile> tiles = tileRepository.findByPlayer(player);
        if (tiles.isEmpty()) {
            List<FarmplotTile> newTiles = IntStream.rangeClosed(1, 9)
                    .mapToObj(i -> FarmplotTile.builder()
                            .tileNum(i)
                            .player(player)
                            .build())
                    .toList();
            tileRepository.saveAll(newTiles);
            tiles = newTiles;
        }

        List<TileResponse> tileResponses = new ArrayList<>();
        for (FarmplotTile tile : tiles) {
            PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);
            tileResponses.add(TileResponse.from(tile, plant));
        }

        return new FarmResponse(tileResponses);
    }

    @Transactional(readOnly = true)
    public TileResponse getTile(Long userId, int x, int y) {
        Player player = findPlayerOrThrow(userId);

        int tileNum = toTileNum(x, y);
        FarmplotTile tile = tileRepository.findByPlayerAndTileNum(player, tileNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.FARMTILE_NOT_FOUND));


        PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);
        return TileResponse.from(tile, plant);
    }

    public TileResponse updateTile(Long userId, TileUpdateRequest request) {
        Player player = findPlayerOrThrow(userId);

        int tileNum = toTileNum(request.x(), request.y());
        FarmplotTile tile = tileRepository.findByPlayerAndTileNum(player, tileNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.FARMTILE_NOT_FOUND));

        PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);
        PlantStatus newStatus = PlantStatus.fromString(request.status());

        if (newStatus == PlantStatus.EMPTY) {
            if (plant != null) {
                plantRepository.delete(plant);
            }
            return TileResponse.from(tile, null);
        }

        if (newStatus == PlantStatus.PLANTED || newStatus == PlantStatus.READY) {
            if (request.plantedAt() == null || request.sunlightCount() == null) {
                throw new BusinessException(ErrorCode.MISSING_REQUIRED_FIELD);
            }
        }

        if (plant == null) {
            plant = PlantedPlant.createNewPlant(tile, player, request);
            plantRepository.save(plant);
        } else {
            plant.updatePlantState(newStatus, request.sunlightCount(), request.plantedAt());
        }

        return TileResponse.from(tile, plant);
    }
}
