package org.farmsystem.homepage.domain.apply.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.apply.dto.request.CreateApplyRequestDto;
import org.farmsystem.homepage.domain.apply.dto.request.ApplyRequestDto;
import org.farmsystem.homepage.domain.apply.dto.request.LoadApplyRequestDto;
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

    @PostMapping("/info")
    public ResponseEntity<SuccessResponse<?>> createApply(@RequestBody @Valid CreateApplyRequestDto request) {
        return SuccessResponse.created(applyService.createApply(request));
    }

    @PostMapping("/save")
    public ResponseEntity<SuccessResponse<?>> saveApply(@RequestBody @Valid ApplyRequestDto request) {
        return SuccessResponse.ok(applyService.saveApply(request, false));
    }

    @PostMapping("/submit")
    public ResponseEntity<SuccessResponse<?>> submitApply(@RequestBody @Valid ApplyRequestDto request) {
        return SuccessResponse.ok(applyService.saveApply(request, true));
    }

    @PostMapping("/load")
    public ResponseEntity<SuccessResponse<?>> loadApply(@RequestBody @Valid LoadApplyRequestDto request) {
        return SuccessResponse.ok(applyService.loadApply(request));
    }
}
