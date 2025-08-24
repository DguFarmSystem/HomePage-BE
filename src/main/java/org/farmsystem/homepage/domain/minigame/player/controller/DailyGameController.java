package org.farmsystem.homepage.domain.minigame.player.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.dto.request.GameTypeRequestDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.response.GameCountResponseDTO;
import org.farmsystem.homepage.domain.minigame.player.service.DailyGameService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/daily-game")
@RequiredArgsConstructor
public class DailyGameController {

    private final DailyGameService dailyGameService;

    // 게임 횟수 조회
    @GetMapping("/{gameType}")
    public ResponseEntity<SuccessResponse<?>> getGameCount(
            @AuthenticationPrincipal Long userId,
            @PathVariable String gameType
    ) {
        GameCountResponseDTO response = dailyGameService.getGameCount(userId, gameType);
        return SuccessResponse.ok(response);
    }

    // 게임 횟수 1회 소모
    @PatchMapping("/use")
    public ResponseEntity<SuccessResponse<?>> useGame(
            @AuthenticationPrincipal Long userId,
            @RequestBody GameTypeRequestDTO request
    ) {
        GameCountResponseDTO response = dailyGameService.useGame(userId, request);
        return SuccessResponse.ok(response);
    }
}
