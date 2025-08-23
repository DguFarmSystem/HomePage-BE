package org.farmsystem.homepage.domain.minigame.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.inventory.dto.request.InventoryUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.inventory.dto.request.InventoryPlantUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.inventory.dto.response.InventoryResponseDTO;
import org.farmsystem.homepage.domain.minigame.inventory.service.InventoryService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getInventory(@AuthenticationPrincipal Long userId){
        List<InventoryResponseDTO> inventoryList = inventoryService.getInventory(userId);
        return SuccessResponse.ok(inventoryList);
    }

    @PostMapping("/update/object")
    public ResponseEntity<SuccessResponse<?>> updateInventoryObject(
            @AuthenticationPrincipal Long userId,
            @RequestBody InventoryUpdateRequestDTO requestDTO
    ){
        List<InventoryResponseDTO> inventoryList = inventoryService.updateInventoryObject(userId, requestDTO);
        return SuccessResponse.ok(inventoryList);
    }





}
