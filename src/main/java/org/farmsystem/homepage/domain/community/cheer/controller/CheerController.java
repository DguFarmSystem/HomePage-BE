package org.farmsystem.homepage.domain.community.cheer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.community.cheer.dto.request.CheerRequestDTO;
import org.farmsystem.homepage.domain.community.cheer.service.CheerService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cheer")
public class CheerController implements CheerApi {

    private final CheerService cheerService;

    // 응원 리스트 조회 API
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getAllCheer(Pageable pageable) {
        return SuccessResponse.ok(cheerService.getAllCheer(pageable));
    }

    // 응원 등록 API
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createCheer(@RequestBody @Valid CheerRequestDTO request) {
        return SuccessResponse.created(cheerService.createCheer(request));
    }

    // 특정 응원 조회
    @GetMapping("/{cheerId}")
    public ResponseEntity<SuccessResponse<?>> getCheerById(@PathVariable Long cheerId) {
        return SuccessResponse.ok(cheerService.getCheer(cheerId));
    }
}
