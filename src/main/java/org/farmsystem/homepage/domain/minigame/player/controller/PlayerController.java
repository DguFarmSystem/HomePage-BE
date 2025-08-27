package org.farmsystem.homepage.domain.minigame.player.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.dto.PlayerCurrencyDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.request.LevelUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.response.PlayerResponseDTO;
import org.farmsystem.homepage.domain.minigame.player.service.PlayerService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/player")
public class PlayerController {

    private final PlayerService playerService;

    //플레이어의 재화 조회
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getPlayerCurrency(
            @AuthenticationPrincipal Long userId
    ) {
        PlayerResponseDTO response = playerService.getPlayerCurrency(userId);
        return SuccessResponse.ok(response);
    }

    //재화 업데이트
    @PatchMapping("/currency")
    public ResponseEntity<SuccessResponse<?>> updatePlayerCurrency(
            @AuthenticationPrincipal Long userId,
            @RequestBody PlayerCurrencyDTO requestDTO
    ){
        PlayerCurrencyDTO response = playerService.updateCurrency(userId, requestDTO);
        return SuccessResponse.ok(response);
    }

    @PatchMapping("/level")
    public ResponseEntity<SuccessResponse<?>> updatePlayerLevel(
            @AuthenticationPrincipal Long userId,
            @RequestBody LevelUpdateRequestDTO requestDTO
    ){
        PlayerResponseDTO response = playerService.updateLevel(userId, requestDTO);
        return SuccessResponse.ok(response);
    }

    @PostMapping("/init")
    public ResponseEntity<SuccessResponse<?>> initPlayer(@AuthenticationPrincipal Long userId) {
        PlayerResponseDTO response = playerService.createPlayer(userId);
        return SuccessResponse.ok(response);
    }


}