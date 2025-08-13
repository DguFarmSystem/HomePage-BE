package org.farmsystem.homepage.domain.minigame.solarstation.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.solarstation.dto.SolarDTO;
import org.farmsystem.homepage.domain.minigame.solarstation.service.SolarService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/solarstation")
public class SolarController {

    private final SolarService solarService;

    @GetMapping
    public ResponseEntity<SolarDTO> getSolarStation(
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(solarService.getSolarStation(userId));
    }

    @PatchMapping("/chargetime")
    public ResponseEntity<SolarDTO> updateChargeTime(
            @AuthenticationPrincipal Long userId,
            @RequestBody SolarDTO request) {
        return ResponseEntity.ok(solarService.updateChargeTime(userId, request));
    }
}
