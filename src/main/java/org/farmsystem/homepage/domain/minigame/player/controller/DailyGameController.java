package org.farmsystem.homepage.domain.minigame.player.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.dto.request.GameTypeRequestDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.response.GameCountResponseDTO;
import org.farmsystem.homepage.domain.minigame.player.service.DailyGameService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/daily-game")
@RequiredArgsConstructor
public class DailyGameController {

    private final DailyGameService dailyGameService;

    // 게임 횟수 조회
    @GetMapping("/{gameType}")
    public GameCountResponseDTO getGameCount(
            @AuthenticationPrincipal Long userId,
            @PathVariable String gameType
    ) {
        return dailyGameService.getGameCount(userId, gameType);
    }

    // 게임 횟수 증가
    @PatchMapping("/increment")
    public GameCountResponseDTO incrementGameCount(
            @AuthenticationPrincipal Long userId,
            @RequestBody GameTypeRequestDTO request
    ) {
        return dailyGameService.incrementGameCount(userId, request);
    }
}
