package org.farmsystem.homepage.domain.minigame.farm.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.farm.dto.request.TileUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.farm.dto.response.TileResponseDTO;
import org.farmsystem.homepage.domain.minigame.farm.service.FarmService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farm")
public class FarmController {

    private final FarmService farmService;

    // 농장 전체 조회
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getFarm(@AuthenticationPrincipal Long userId) {
        List<TileResponseDTO> response = farmService.getFarm(userId);
        return SuccessResponse.ok(response);
    }

    // 특정 타일 조회
    @GetMapping("/tile")
    public ResponseEntity<SuccessResponse<?>> getTile(
            @AuthenticationPrincipal Long userId,
            @RequestParam int x,
            @RequestParam int y
    ) {
        TileResponseDTO response = farmService.getTile(userId, x, y);
        return SuccessResponse.ok(response);
    }

    // 특정 타일 업데이트
    @PatchMapping("/tile")
    public ResponseEntity<SuccessResponse<?>> updateTile(
            @AuthenticationPrincipal Long userId,
            @RequestBody TileUpdateRequestDTO request
    ) {
        TileResponseDTO response = farmService.updateTile(userId, request);
        return SuccessResponse.ok(response);
    }
}
