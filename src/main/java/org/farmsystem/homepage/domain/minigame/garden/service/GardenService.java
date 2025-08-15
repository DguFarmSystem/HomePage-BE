package org.farmsystem.homepage.domain.minigame.garden.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.garden.dto.AddTileDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.request.MoveObjectRequestDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.request.PlaceObjectRequestDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.GardenResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.MoveObjectResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.PlaceObjectResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.PlacedObjectResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.entity.GardenTile;
import org.farmsystem.homepage.domain.minigame.garden.entity.PlacedObject;
import org.farmsystem.homepage.domain.minigame.garden.repository.GardenTileRepository;
import org.farmsystem.homepage.domain.minigame.garden.repository.PlacedObjectRepository;
import org.farmsystem.homepage.domain.minigame.inventory.entity.ObjectInventory;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Store;
import org.farmsystem.homepage.domain.minigame.inventory.repository.ObjectInventoryRepository;
import org.farmsystem.homepage.domain.minigame.inventory.repository.StoreRepository;
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
    private final ObjectInventoryRepository objectInventoryRepository;
    private final StoreRepository storeRepository;

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

        //타일이 깔린 곳에 배치하려고 하는 게 맞는지 검증
        GardenTile tile = gardenTileRepository
                .findByPlayerAndXAndY(player, requestDTO.x(), requestDTO.y())
                .orElseThrow(() -> new BusinessException(ErrorCode.TILE_NOT_FOUND));

        //해당 타일에 이미 오브젝트 있는지 확인
        if (placedObjectRepository.existsByTile(tile)) {
            throw new BusinessException(ErrorCode.OBJECT_ALREADY_PLACED);
        }

        //보유 수량 확인 (store_goods_number 기반)
        List<ObjectInventory> ownedList = objectInventoryRepository
                .findByPlayerAndObjectKind_StoreGoodsNumber(player, requestDTO.objectType());  //objectType은 숫자임

        if (ownedList.isEmpty()) {
            throw new BusinessException(ErrorCode.OBJECT_NOT_OWNED);
        }

        //배치 (1. 첫번째 행 하나 삭제, 2. PlacedObject에 데이터 추가)
        ObjectInventory owned = ownedList.get(0);
        Store store = owned.getObjectKind();
        objectInventoryRepository.delete(owned);

        PlacedObject object = PlacedObject.builder()
                .tile(tile)
                .objectKind(store)
                .rotation(requestDTO.rotation())
                .build();

        placedObjectRepository.save(object);

        return new PlaceObjectResponseDTO(requestDTO.x(), requestDTO.y(), store.getStoreGoodsNumber(), requestDTO.rotation());
    }

    public MoveObjectResponseDTO moveGardenObject(Long userId, MoveObjectRequestDTO requestDTO) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        //현재 타일 확인
        GardenTile fromTile = gardenTileRepository
                .findByPlayerAndXAndY(player, requestDTO.fromX(), requestDTO.fromY())
                .orElseThrow(() -> new BusinessException(ErrorCode.TILE_NOT_FOUND));

        //현재 위치에 배치된 오브젝트 조회
        PlacedObject placedObject = placedObjectRepository.findByTile(fromTile)
                .orElseThrow(() -> new BusinessException(ErrorCode.OBJECT_NOT_FOUND));

        //타일에 위치한 오브젝트와 요청 데이터의 오브젝트 종류 일치 여부 확인
        if (!placedObject.getObjectKind().getStoreGoodsNumber().equals(requestDTO.objectType())) {
            throw new BusinessException(ErrorCode.OBJECT_TYPE_MISMATCH);
        }

        //이동할 타일
        GardenTile toTile = gardenTileRepository
                .findByPlayerAndXAndY(player, requestDTO.toX(), requestDTO.toY())
                .orElseThrow(() -> new BusinessException(ErrorCode.TILE_NOT_FOUND));

        //이동할 타일에 이미 오브젝트 있는지 확인
        if (placedObjectRepository.existsByTile(toTile)) {
            throw new BusinessException(ErrorCode.OBJECT_ALREADY_PLACED);
        }

        //오브젝트 위치 변경
        placedObject.updatePlacedLocation(toTile);
        placedObjectRepository.save(placedObject);

        return new MoveObjectResponseDTO(
                requestDTO.toX(),
                requestDTO.toY(),
                requestDTO.objectType()
        );
    }




}
