package org.farmsystem.homepage.domain.minigame.solar.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.solar.dto.SolarDTO;
import org.farmsystem.homepage.domain.minigame.solar.service.SolarService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//TODO: 띄어쓰기 필요한 부분 대시 처리해주면 좋을 듯. 게임파트와 재연결 필요 (/api/solar-station) (수정사항 있으면 꼭 전달하기)
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
    // TODO: 위와 같은 이유로 chargetime이라는 경로도 정리하면 좋을듯.
    @PatchMapping("/chargetime")
    public ResponseEntity<SuccessResponse<?>> updateChargeTime(
            @AuthenticationPrincipal Long userId,
            @RequestBody SolarDTO request
    ) {
        SolarDTO response = solarService.updateChargeTime(userId, request);
        return SuccessResponse.ok(response);
    }
}
