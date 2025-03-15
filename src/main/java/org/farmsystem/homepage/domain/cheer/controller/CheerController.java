package org.farmsystem.homepage.domain.cheer.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.cheer.service.CheerService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
