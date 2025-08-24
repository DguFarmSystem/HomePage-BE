package org.farmsystem.homepage.domain.minigame.garden.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.garden.dto.AddTileDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.request.ChangePlacedObjectRequestDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.request.PlaceObjectRequestDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.GardenResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.ChangePlacedObjectResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.PlaceObjectResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.PlacedObjectResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.entity.GardenTile;
import org.farmsystem.homepage.domain.minigame.garden.entity.PlacedObject;
import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;
import org.farmsystem.homepage.domain.minigame.garden.repository.GardenTileRepository;
import org.farmsystem.homepage.domain.minigame.garden.repository.PlacedObjectRepository;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Inventory;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Store;
import org.farmsystem.homepage.domain.minigame.inventory.repository.InventoryRepository;
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
    private final InventoryRepository inventoryRepository;

    private GardenTile getGardenTile(Player player, Long x, Long y) {
        return gardenTileRepository.findByPlayerAndXAndY(player, x, y)
                .orElseThrow(() -> new BusinessException(ErrorCode.GARDENTILE_NOT_FOUND));
    }

    private PlacedObject getPlacedObject(GardenTile tile) {
        return placedObjectRepository.findByTile(tile)
                .orElseThrow(() -> new BusinessException(ErrorCode.OBJECT_NOT_FOUND));
    }


    @Transactional(readOnly = true)
    public List<GardenResponseDTO> getGardenInfo(Long userId) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        List<GardenResponseDTO> gardenList = gardenTileRepository.findByPlayer(player).stream()
                .map(tile -> {
                    PlacedObject obj = placedObjectRepository.findByTile(tile).orElse(null);

                    PlacedObjectResponseDTO objDTO = PlacedObjectResponseDTO.from(obj);

                    return GardenResponseDTO.from(tile, objDTO);
                })
                .toList();

        return gardenList;
    }

    //타일 추가
    public AddTileDTO addGardenTile(Long userId, AddTileDTO addTileDTO) {
        //유저 확인
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        //이미 깔린 타일 좌표인지 확인
        boolean exists = gardenTileRepository.existsByPlayerAndXAndY(player, addTileDTO.x(), addTileDTO.y());
        if (exists) {
            throw new BusinessException(ErrorCode.GARDEN_TILE_ALREADY_EXISTS);
        }

        //타일 좌표 DB에 추가
        GardenTile gardenTile = GardenTile.builder()
                .x(addTileDTO.x())
                .y(addTileDTO.y())
                .tileType(addTileDTO.tileType())
                .player(player)
                .build();

        gardenTileRepository.save(gardenTile);
        return AddTileDTO.from(gardenTile);
    }

    //정원 오브젝트 새로 배치
    public PlaceObjectResponseDTO addGardenObject(Long userId, PlaceObjectRequestDTO requestDTO) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));


        //플레이어 정원에 타일이 깔린 곳에 배치하려고 하는 게 맞는지 검증
        GardenTile tile = getGardenTile(player, requestDTO.x(), requestDTO.y());

        //해당 타일에 이미 오브젝트 있는지 확인
        if (placedObjectRepository.existsByTile(tile)) {
            throw new BusinessException(ErrorCode.OBJECT_ALREADY_PLACED);
        }
        //보유 오브젝트 조회
        Inventory owned = inventoryRepository
                .findFirstByPlayerAndObjectKind_StoreGoodsNumberOrderByOwnedIdAsc(player, requestDTO.objectType())
                .orElseThrow(() -> new BusinessException(ErrorCode.OBJECT_NOT_OWNED));

        Store store = owned.getObjectKind();

        //보유 오브젝트 개수 확인
        int objCount = inventoryRepository.countByPlayerAndObjectKind_StoreGoodsNumber(player, requestDTO.objectType());
        inventoryRepository.delete(owned);
        objCount = objCount - 1;

        PlacedObject object = PlacedObject.builder()
                .tile(tile)
                .objectKind(store)
                .rotation(requestDTO.rotation())
                .build();

        placedObjectRepository.save(object);
        return PlaceObjectResponseDTO.from(object, objCount);
    }

    //배치된 오브젝트 위치 변경
    public ChangePlacedObjectResponseDTO moveGardenObject(Long userId, ChangePlacedObjectRequestDTO requestDTO) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        GardenTile fromTile = getGardenTile(player, requestDTO.fromX(), requestDTO.fromY()); //현재 타일 확인
        PlacedObject placedObject = getPlacedObject(fromTile); //타일 위 배치된 오브젝트 조회

        //타일에 위치한 오브젝트와 요청 데이터의 오브젝트 종류 일치 여부 확인
        if (!placedObject.getObjectKind().getStoreGoodsNumber().equals(requestDTO.objectType())) {
            throw new BusinessException(ErrorCode.OBJECT_TYPE_MISMATCH);
        }

        GardenTile toTile = getGardenTile(player, requestDTO.toX(), requestDTO.toY());  //이동할 타일

        //이동할 타일에 이미 오브젝트 있는지 확인
        if (placedObjectRepository.existsByTile(toTile)) {
            throw new BusinessException(ErrorCode.OBJECT_ALREADY_PLACED);
        }

        //오브젝트 위치 변경
        placedObject.updatePlacedLocation(toTile);
        placedObjectRepository.save(placedObject);

        return ChangePlacedObjectResponseDTO.from(placedObject);
    }

    //배치된 오브젝트 회전 각도 변경
    public ChangePlacedObjectResponseDTO rotateGardenObject(Long userId, PlaceObjectRequestDTO requestDTO) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        GardenTile tile = getGardenTile(player, requestDTO.x(), requestDTO.y()); //현재 타일 확인
        PlacedObject placedObject = getPlacedObject(tile);  //타일 위 오브젝트 확인

        if (!placedObject.getObjectKind().getStoreGoodsNumber().equals(requestDTO.objectType())) {
            throw new BusinessException(ErrorCode.OBJECT_TYPE_MISMATCH);
        }

        //회전 각도 변경
        Rotation newRotation = requestDTO.rotation();
        placedObject.updateRotation(newRotation);
        placedObjectRepository.save(placedObject);

        return ChangePlacedObjectResponseDTO.from(placedObject);
    }

    //배치 철회
    public PlaceObjectResponseDTO removeGardenObject(Long userId, PlaceObjectRequestDTO requestDTO) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        GardenTile tile = getGardenTile(player, requestDTO.x(), requestDTO.y());  //현재 타일 확인
        PlacedObject placedObject = getPlacedObject(tile);  //타일 위 오브젝트 확인

        if (!placedObject.getObjectKind().getStoreGoodsNumber().equals(requestDTO.objectType())) {
            throw new BusinessException(ErrorCode.OBJECT_TYPE_MISMATCH);
        }

        int objCount = inventoryRepository.countByPlayerAndObjectKind_StoreGoodsNumber(player, requestDTO.objectType());

        // placed_object 테이블에서 삭제
        placedObjectRepository.delete(placedObject);

        //인벤토리에 1개 추가
        Inventory inventory = Inventory.builder()
                .player(player)
                .objectKind(placedObject.getObjectKind()) // (objectKind는 Store 타입임)
                .build();
        inventoryRepository.save(inventory);
        objCount = objCount + 1;

        return PlaceObjectResponseDTO.from(placedObject, objCount);
    }
}