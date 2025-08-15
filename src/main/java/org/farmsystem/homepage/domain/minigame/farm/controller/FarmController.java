package org.farmsystem.homepage.domain.minigame.farm.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.farm.dto.request.TileUpdateRequest;
import org.farmsystem.homepage.domain.minigame.farm.dto.response.FarmResponse;
import org.farmsystem.homepage.domain.minigame.farm.dto.response.TileResponse;
import org.farmsystem.homepage.domain.minigame.farm.service.FarmService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farm")
public class FarmController {

    private final FarmService farmService;

    @GetMapping
    public ResponseEntity<FarmResponse> getFarm(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(farmService.getFarm(userId));
    }

    @GetMapping("/tile")
    public ResponseEntity<TileResponse> getTile(
            @AuthenticationPrincipal Long userId,
            @RequestParam int x,
            @RequestParam int y
    ) {
        return ResponseEntity.ok(farmService.getTile(userId, x, y));
    }

    @PatchMapping("/tile")
    public ResponseEntity<TileResponse> updateTile(
            @AuthenticationPrincipal Long userId,
            @RequestBody TileUpdateRequest request
    ) {
        return ResponseEntity.ok(farmService.updateTile(userId, request));
    }
}
