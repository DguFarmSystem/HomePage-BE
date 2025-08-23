package org.farmsystem.homepage.domain.minigame.player.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.dto.request.BadgeUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.response.BadgeResponseDTO;
import org.farmsystem.homepage.domain.minigame.player.service.BadgeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/badge")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    // 전체 칭호 조회
    @GetMapping
    public List<BadgeResponseDTO> getBadges(@AuthenticationPrincipal Long userId) {
        return badgeService.getBadges(userId);
    }

    // 칭호 추가
    @PostMapping
    public BadgeResponseDTO addBadge(
            @AuthenticationPrincipal Long userId,
            @RequestBody BadgeUpdateRequestDTO request
    ) {
        return badgeService.addBadge(userId, request);
    }
}
