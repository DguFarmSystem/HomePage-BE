package org.farmsystem.homepage.domain.minigame.dex.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.dex.dto.request.DexRequestDTO;
import org.farmsystem.homepage.domain.minigame.dex.dto.response.DexResponseDTO;
import org.farmsystem.homepage.domain.minigame.dex.service.DexService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dex")
@RequiredArgsConstructor
public class DexController {

    private final DexService dexService;

    // 도감 전체 목록 조회 API
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getDexList(
            @AuthenticationPrincipal Long userId
    ) {
        List<DexResponseDTO> response = dexService.getDexList(userId);
        return SuccessResponse.ok(response);
    }

    // 도감 등록 API
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> addDex(
            @AuthenticationPrincipal Long userId,
            @RequestBody DexRequestDTO request
    ) {
        DexResponseDTO response = dexService.addDex(userId, request);
        return SuccessResponse.created(response);
    }

}
