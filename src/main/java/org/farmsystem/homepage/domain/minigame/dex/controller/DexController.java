package org.farmsystem.homepage.domain.minigame.dex.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.dex.dto.request.DexRequest;
import org.farmsystem.homepage.domain.minigame.dex.dto.response.DexListResponse;
import org.farmsystem.homepage.domain.minigame.dex.dto.response.DexResponse;
import org.farmsystem.homepage.domain.minigame.dex.service.DexService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dex")
@RequiredArgsConstructor
public class DexController {

    private final DexService dexService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DexResponse addDex(
            @AuthenticationPrincipal Long userId,
            @RequestBody DexRequest request
    ) {
        return dexService.addDex(userId, request);
    }

    @GetMapping
    public DexListResponse getDexList(
            @AuthenticationPrincipal Long userId
    ) {
        return dexService.getDexList(userId);
    }
}
