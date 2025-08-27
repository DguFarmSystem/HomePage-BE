package org.farmsystem.homepage.domain.minigame.solarstation.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.solarstation.dto.SolarDTO;
import org.farmsystem.homepage.domain.minigame.solarstation.service.SolarService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/solarstation")
public class SolarController {

    private final SolarService solarService;
    // 플레이어의 태양광 발전소 충전시작시간 조회
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getSolarStation(@AuthenticationPrincipal Long userId) {
        SolarDTO response = solarService.getSolarStation(userId);
        return SuccessResponse.ok(response);
    }

    // 플레이어의 태양광 발전소 충전시작시간 업데이트
    @PatchMapping("/chargetime")
    public ResponseEntity<SuccessResponse<?>> updateChargeTime(
            @AuthenticationPrincipal Long userId,
            @RequestBody SolarDTO request
    ) {
        SolarDTO response = solarService.updateChargeTime(userId, request);
        return SuccessResponse.ok(response);
    }
}
