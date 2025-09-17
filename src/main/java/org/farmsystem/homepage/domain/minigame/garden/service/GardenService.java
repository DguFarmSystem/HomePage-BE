package org.farmsystem.homepage.domain.minigame.garden.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.garden.dto.request.PlaceObjectRequestDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.request.UpdateGardenObjectRequestDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.request.UpdateGardenRequestDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.*;
import org.farmsystem.homepage.domain.minigame.garden.entity.GardenTile;
import org.farmsystem.homepage.domain.minigame.garden.entity.PlacedObject;
import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;
import org.farmsystem.homepage.domain.minigame.garden.repository.GardenTileRepository;
import org.farmsystem.homepage.domain.minigame.garden.repository.PlacedObjectRepository;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Goods;
import org.farmsystem.homepage.domain.minigame.inventory.repository.GoodsRepository;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.player.repository.PlayerRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GardenService {

    private final GardenTileRepository gardenTileRepository;
    private final PlacedObjectRepository placedObjectRepository;
    private final PlayerRepository playerRepository;
    private final GoodsRepository goodsRepository;

    private static final long DEFAULT_GRASS_TILE_NUMBER = 400010L;

    private Player getPlayerOrThrow(Long userId) {
        return playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));
    }
    
    private GardenTile getGardenTile(Player player, Long x, Long y) {
        return gardenTileRepository.findByPlayerAndXAndY(player, x, y)
                .orElseThrow(() -> new BusinessException(ErrorCode.GARDENTILE_NOT_FOUND));
    }

    private PlacedObject getPlacedObject(GardenTile tile) {
        return placedObjectRepository.findByTile(tile)
                .orElseThrow(() -> new BusinessException(ErrorCode.OBJECT_NOT_FOUND));
    }
    
    //요청 데이터의 타일 타입이 기본 잔디 타일인지 확인
    private boolean isDefaultGrass(Goods goods) {
        return goods.getGoodsNumber() == DEFAULT_GRASS_TILE_NUMBER;
    }

    //상점에 존재하는 넘버인지 확인
    private Goods findStoreOrThrow(Long goodsNumber) {
        return goodsRepository.findByGoodsNumber(goodsNumber)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
    }


    @Transactional(readOnly = true)
    public List<GardenResponseDTO> getGardenInfo(Long userId) {
        Player player = getPlayerOrThrow(userId);

        List<GardenResponseDTO> gardenList = gardenTileRepository.findByPlayerOrderByXAscYAsc(player).stream()
                .map(tile -> {
                    PlacedObject obj = placedObjectRepository.findByTile(tile).orElse(null);

                    PlacedObjectResponseDTO objDTO = PlacedObjectResponseDTO.from(obj);

                    return GardenResponseDTO.from(tile, objDTO);
                })
                .toList();

        return gardenList;
    }

    // 정원 단일 타일 최종 상태 업데이트
    public UpdateGardenResponseDTO updateGarden(Long userId, int x, int y, UpdateGardenRequestDTO req) {
        Player player = getPlayerOrThrow(userId);
        return applyTileFinalState(player, x, y, req);
    }

    //배치된 오브젝트 회전 각도 변경
    public ChangePlacedObjectResponseDTO rotateGardenObject(Long userId, PlaceObjectRequestDTO requestDTO) {
        Player player = getPlayerOrThrow(userId);

        GardenTile tile = getGardenTile(player, requestDTO.x(), requestDTO.y()); //현재 타일 확인
        PlacedObject placedObject = getPlacedObject(tile);  //타일 위 오브젝트 확인

        if (!placedObject.getObjectType().getGoodsNumber().equals(requestDTO.objectType())) {
            throw new BusinessException(ErrorCode.OBJECT_TYPE_MISMATCH);
        }

        //회전 각도 변경
        Rotation newRotation = requestDTO.rotation();
        placedObject.updateRotation(newRotation);
        placedObjectRepository.save(placedObject);

        return ChangePlacedObjectResponseDTO.from(placedObject);
    }
    
    private UpdateGardenResponseDTO applyTileFinalState(Player player, int x, int y, UpdateGardenRequestDTO requestDTO) {
        GardenTile tile = gardenTileRepository.findByPlayerAndXAndY(player, (long) x, (long) y).orElse(null);

        Goods newTileType = findStoreOrThrow(requestDTO.tileType());
        UpdateGardenObjectRequestDTO requestObject = requestDTO.object();

        // 기본 잔디 & 오브젝트 없음 -> 기본 상태로 초기화
        if (isDefaultGrass(newTileType) && requestObject == null) {
            if (tile != null) {
                placedObjectRepository.deleteByTile(tile);
                gardenTileRepository.delete(tile);
            }
            return new UpdateGardenResponseDTO(x, y, newTileType.getGoodsNumber(), null);
        }

        //타일 생성 또는 업데이트
        if (tile == null) {
            tile = GardenTile.createGardenTile(player, x, y, newTileType);
        } else {
            tile.updateTileType(newTileType);
        }
        gardenTileRepository.save(tile);

        //PlacedObject 삭제/생성/업데이트
        UpdateGardenObjectResponseDTO responseObject = null;
        if (requestObject == null) {
            placedObjectRepository.deleteByTile(tile);
        } else {
            Goods newObjectType = findStoreOrThrow(requestObject.objectType());
            PlacedObject placed = placedObjectRepository.findByTile(tile).orElse(null);

            if (placed == null) {
                placed = PlacedObject.createPlacedObject(tile, newObjectType, requestObject.rotation());
            } else {
                placed.updatePlacedLocation(tile);
                placed.updateRotation(requestObject.rotation());
                placed.updateObjectType(newObjectType);
            }
            placedObjectRepository.save(placed);

            responseObject = new UpdateGardenObjectResponseDTO(
                    placed.getObjectType().getGoodsNumber(),
                    placed.getRotation()
            );
        }

        return new UpdateGardenResponseDTO(
                x, y,
                tile.getTileType().getGoodsNumber(),
                responseObject
        );
    }

}