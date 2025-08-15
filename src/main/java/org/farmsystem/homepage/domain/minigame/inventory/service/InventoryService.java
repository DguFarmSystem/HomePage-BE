package org.farmsystem.homepage.domain.minigame.inventory.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.inventory.dto.request.InventoryObjectUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.inventory.dto.request.InventoryPlantUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.inventory.dto.response.InventoryResponseDTO;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final ObjectInventoryRepository objectInventoryRepository;
    private final PlayerRepository playerRepository;
    private final StoreRepository storeRepository;

    private List<ObjectInventory> addToInventory(Player player, Store store, int count) {
        List<ObjectInventory> temp = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            temp.add(ObjectInventory.builder()
                    .player(player)
                    .objectKind(store)
                    .build());
        }
        return temp;
    }

    //인벤토리 조회
    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> getInventory(Long userId){
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        return objectInventoryRepository.countInventoryGroupByObjectKind(player);
    }

    //인벤토리 오브젝트 추가
    public List<InventoryResponseDTO> updateInventoryObject(Long userId, InventoryObjectUpdateRequestDTO requestDTO){
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        Store store = storeRepository.findByStoreGoodsNumber(requestDTO.objectType())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        List<ObjectInventory> temp = addToInventory(player, store, requestDTO.objectCount());
        objectInventoryRepository.saveAll(temp);
        
        return objectInventoryRepository.countInventoryGroupByObjectKind(player);
    }

    //인벤토리 식물 추가
    public List<InventoryResponseDTO> updateInventoryPlant(Long userId, InventoryPlantUpdateRequestDTO requestDTO){
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        Store store = storeRepository.findByStoreGoodsName(String.valueOf(requestDTO.plantType()))
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        List<ObjectInventory> temp = addToInventory(player, store, requestDTO.plantCount());
        objectInventoryRepository.saveAll(temp);

        return objectInventoryRepository.countInventoryGroupByObjectKind(player);
    }


}
