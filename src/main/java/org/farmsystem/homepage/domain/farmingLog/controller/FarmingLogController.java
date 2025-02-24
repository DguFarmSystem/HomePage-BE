package org.farmsystem.homepage.domain.farmingLog.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.farmingLog.dto.FarmingLogRequestDto;
import org.farmsystem.homepage.domain.farmingLog.dto.FarmingLogResponseDto;
import org.farmsystem.homepage.domain.farmingLog.service.FarmingLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farming-logs")
public class FarmingLogController {

    private final FarmingLogService farmingLogService;

    @GetMapping
    public Page<FarmingLogResponseDto> getAllFarmingLogs(@AuthenticationPrincipal Long userId, Pageable pageable) {
        return farmingLogService.getAllFarmingLogs(userId, pageable);
    }

    @GetMapping("/my")
    public Page<FarmingLogResponseDto> getMyFarmingLogs(@AuthenticationPrincipal Long userId, Pageable pageable) {
        return farmingLogService.getMyFarmingLogs(userId, pageable);
    }

    @PostMapping
    public FarmingLogResponseDto createFarmingLog(@AuthenticationPrincipal Long userId,
                                                  @RequestBody FarmingLogRequestDto requestDto) {
        return farmingLogService.createFarmingLog(userId, requestDto);
    }

    @PatchMapping("/{farmingLogId}")
    public FarmingLogResponseDto updateFarmingLog(@AuthenticationPrincipal Long userId,
                                                  @PathVariable Long farmingLogId,
                                                  @RequestBody FarmingLogRequestDto requestDto) {
        return farmingLogService.updateFarmingLog(userId, farmingLogId, requestDto);
    }

    @DeleteMapping("/{farmingLogId}")
    public void deleteFarmingLog(@AuthenticationPrincipal Long userId,
                                 @PathVariable Long farmingLogId) {
        farmingLogService.deleteFarmingLog(userId, farmingLogId);
    }
}
