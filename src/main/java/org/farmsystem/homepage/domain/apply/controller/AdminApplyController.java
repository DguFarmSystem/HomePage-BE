package org.farmsystem.homepage.domain.apply.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.apply.service.ApplyService;
import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/apply")
public class AdminApplyController implements AdminApplyApi {

    private final ApplyService applyService;

    // 지원서 목록 API
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getApplyList(@RequestParam(required = false) Track track) {
        return SuccessResponse.ok(applyService.getApplyList(track));
    }

    // 지원서 상세 API
    @GetMapping("{applyId}")
    public ResponseEntity<SuccessResponse<?>> getApply(@PathVariable Long applyId) {
        return SuccessResponse.ok(applyService.getApply(applyId));
    }
}
