package org.farmsystem.homepage.domain.minigame.inventory.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.inventory.dto.request.InventoryUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.inventory.dto.response.InventoryResponseDTO;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Inventory;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Store;
import org.farmsystem.homepage.domain.minigame.inventory.repository.InventoryRepository;
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

    private final InventoryRepository inventoryRepository;
    private final PlayerRepository playerRepository;
    private final StoreRepository storeRepository;

    private List<Inventory> addToInventory(Player player, Store store, int count) {
        List<Inventory> temp = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            temp.add(Inventory.builder()
                    .player(player)
                    .objectKind(store)
                    .build());
        }
        return temp;
    }

    private void deleteInventory(Player player, Long storeGoodsNumber, int count) {
        for (int i = 0; i < count; i++) {
            Inventory oldest = inventoryRepository
                    .findFirstByPlayerAndObjectKind_StoreGoodsNumberOrderByOwnedIdAsc(player, storeGoodsNumber)
                    .orElseThrow(() -> new BusinessException(ErrorCode.OBJECT_NOT_OWNED)); // 삭제 대상 부족
            inventoryRepository.delete(oldest); // 개별 삭제
        }
    }

    private int computeDeltaCount(Player player, Long storeGoodsNumber, int newCount) {
        int current = inventoryRepository.countByPlayerAndObjectKind_StoreGoodsNumber(player, storeGoodsNumber);
        return newCount - current;
    }

    //인벤토리 조회
    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> getInventory(Long userId){
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        return inventoryRepository.countInventoryGroupByObjectKind(player);
    }

    //인벤토리 오브젝트 업데이트
    public List<InventoryResponseDTO> updateInventoryObject(Long userId, InventoryUpdateRequestDTO requestDTO){
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        Store store = storeRepository.findByStoreGoodsNumber(requestDTO.objectType())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        Long storeGoodsNumber = requestDTO.objectType();
        int newCount = requestDTO.objectCount();

        //업데이트할 개수와 현재 개수 차이 계산
        int delta = computeDeltaCount(player, storeGoodsNumber, newCount);

        //행 추가 또는 삭제
        if (delta > 0) {
            List<Inventory> temp = addToInventory(player, store, delta);
            inventoryRepository.saveAll(temp);
        }
        else if (delta < 0) {
            deleteInventory(player, storeGoodsNumber, Math.abs(delta)); //절댓값만큼 행 삭제
        }
        
        return inventoryRepository.countInventoryGroupByObjectKind(player);
    }




}
