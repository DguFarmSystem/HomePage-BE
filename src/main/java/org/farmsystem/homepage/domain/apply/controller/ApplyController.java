package org.farmsystem.homepage.domain.apply.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.apply.dto.request.CreateApplyRequestDTO;
import org.farmsystem.homepage.domain.apply.dto.request.ApplyRequestDTO;
import org.farmsystem.homepage.domain.apply.dto.request.LoadApplyRequestDTO;
import org.farmsystem.homepage.domain.apply.service.ApplyService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apply")
public class ApplyController {

    private final ApplyService applyService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getQuestions() {
        return SuccessResponse.ok(applyService.getQuestions());
    }

    // 지원서 생성
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createApply(@RequestBody @Valid CreateApplyRequestDTO request) {
        return SuccessResponse.created(applyService.createApply(request));
    }

    @PostMapping("/save")
    public ResponseEntity<SuccessResponse<?>> saveApply(@RequestBody @Valid ApplyRequestDTO request) {
        return SuccessResponse.ok(applyService.saveApply(request, false));
    }

    @PostMapping("/submit")
    public ResponseEntity<SuccessResponse<?>> submitApply(@RequestBody @Valid ApplyRequestDTO request) {
        return SuccessResponse.ok(applyService.saveApply(request, true));
    }

    @PostMapping("/load")
    public ResponseEntity<SuccessResponse<?>> loadApply(@RequestBody @Valid LoadApplyRequestDTO request) {
        return SuccessResponse.ok(applyService.loadApply(request));
    }
}
