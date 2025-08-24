package org.farmsystem.homepage.domain.minigame.player.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.dto.request.BadgeUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.response.BadgeResponseDTO;
import org.farmsystem.homepage.domain.minigame.player.service.BadgeService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SuccessResponse<?>> getBadges(@AuthenticationPrincipal Long userId) {
        List<BadgeResponseDTO> response = badgeService.getBadges(userId);
        return SuccessResponse.ok(response);
    }

    // 칭호 추가
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> addBadge(
            @AuthenticationPrincipal Long userId,
            @RequestBody BadgeUpdateRequestDTO request
    ) {
        BadgeResponseDTO response = badgeService.addBadge(userId, request);
        return SuccessResponse.created(response);
    }
}