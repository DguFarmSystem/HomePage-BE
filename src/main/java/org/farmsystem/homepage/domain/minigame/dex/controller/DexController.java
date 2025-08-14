package org.farmsystem.homepage.domain.minigame.dex.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.dex.dto.DexDTO;
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
    public DexDTO.DexResponse addDex(
            @AuthenticationPrincipal Long userId,
            @RequestBody DexDTO.DexRequest request
    ) {
        return dexService.addDex(userId, request);
    }

    @GetMapping
    public DexDTO.DexListResponse getDexList(
            @AuthenticationPrincipal Long userId
    ) {
        return dexService.getDexList(userId);
    }
}
