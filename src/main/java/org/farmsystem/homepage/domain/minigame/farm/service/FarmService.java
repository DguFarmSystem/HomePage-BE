package org.farmsystem.homepage.domain.minigame.farm.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.farm.dto.request.TileUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.farm.dto.response.TileResponseDTO;
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
    // (x,y) 좌표 → DB에 저장된 tileNum(1~9) 변환
    private int toTileNum(int x, int y) {
        return y * 3 + (x + 1);
    }

    // 전체 텃밭(9칸) 조회 API
    @Transactional(readOnly = true)
    public List<TileResponseDTO> getFarm(Long userId) {

        Player player = findPlayerOrThrow(userId);

        // 최초 접속 시 타일이 없으면 9칸 자동 생성
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

        // 각 타일마다 심어진 식물 조회 후 DTO 변환
        List<TileResponseDTO> tileResponses = new ArrayList<>();
        for (FarmplotTile tile : tiles) {
            PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);
            tileResponses.add(TileResponseDTO.from(tile, plant));
        }

        return tileResponses;
    }

    // 단일 타일 조회 API
    @Transactional(readOnly = true)
    public TileResponseDTO getTile(Long userId, int x, int y) {
        Player player = findPlayerOrThrow(userId);

        int tileNum = toTileNum(x, y);
        FarmplotTile tile = tileRepository.findByPlayerAndTileNum(player, tileNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.FARMTILE_NOT_FOUND));

        // 해당 타일에 연결된 식물 조회 후 DTO 반환
        PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);
        return TileResponseDTO.from(tile, plant);
    }

    // 타일 상태 변경 API
    public TileResponseDTO updateTile(Long userId, TileUpdateRequestDTO request) {
        Player player = findPlayerOrThrow(userId);

        int tileNum = toTileNum(request.x(), request.y());
        FarmplotTile tile = tileRepository.findByPlayerAndTileNum(player, tileNum)
                .orElseThrow(() -> new BusinessException(ErrorCode.FARMTILE_NOT_FOUND));

        // 해당 타일에 이미 식물이 심어져 있는지 확인
        PlantedPlant plant = plantRepository.findByFarmplotTile(tile).orElse(null);

        PlantStatus newStatus = PlantStatus.fromString(request.status());

        // 상태를 EMPTY로 변경 → 심어진 식물 삭제
        if (newStatus == PlantStatus.EMPTY) {
            if (plant != null) {
                plantRepository.delete(plant);
            }
            return TileResponseDTO.from(tile, null);
        }

        // PLANTED 또는 READY로 변경 → 필수 필드 검증
        if (newStatus == PlantStatus.PLANTED || newStatus == PlantStatus.READY) {
            if (request.plantedAt() == null || request.sunlightCount() == null) {
                throw new BusinessException(ErrorCode.MISSING_REQUIRED_FIELD);
            }
        }

        // 식물이 없으면 새로 생성, 있으면 상태만 업데이트
        if (plant == null) {
            plant = PlantedPlant.createNewPlant(tile, player, request);
            plantRepository.save(plant);
        } else {
            plant.updatePlantState(newStatus, request.sunlightCount(), request.plantedAt());
        }

        return TileResponseDTO.from(tile, plant);
    }
}
