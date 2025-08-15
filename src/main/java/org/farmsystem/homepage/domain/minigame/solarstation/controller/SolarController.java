package org.farmsystem.homepage.domain.minigame.solarstation.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.solarstation.dto.SolarDTO;
import org.farmsystem.homepage.domain.minigame.solarstation.service.SolarService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/solarstation")
public class SolarController {

    private final SolarService solarService;

    @GetMapping
    public SolarDTO getSolarStation(@AuthenticationPrincipal Long userId) {
        return solarService.getSolarStation(userId);
    }

    @PatchMapping("/chargetime")
    public SolarDTO updateChargeTime(
            @AuthenticationPrincipal Long userId,
            @RequestBody SolarDTO request
    ) {
        return solarService.updateChargeTime(userId, request);
    }
}
