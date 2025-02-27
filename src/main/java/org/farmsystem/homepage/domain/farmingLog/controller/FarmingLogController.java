package org.farmsystem.homepage.domain.farmingLog.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.farmingLog.dto.FarmingLogRequestDTO;
import org.farmsystem.homepage.domain.farmingLog.dto.FarmingLogResponseDTO;
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
    public Page<FarmingLogResponseDTO> getAllFarmingLogs(@AuthenticationPrincipal Long userId, Pageable pageable) {
        return farmingLogService.getAllFarmingLogs(userId, pageable);
    }

    @GetMapping("/my")
    public Page<FarmingLogResponseDTO> getMyFarmingLogs(@AuthenticationPrincipal Long userId, Pageable pageable) {
        return farmingLogService.getMyFarmingLogs(userId, pageable);
    }

    @PostMapping
    public FarmingLogResponseDTO createFarmingLog(@AuthenticationPrincipal Long userId,
                                                  @RequestBody FarmingLogRequestDTO requestDto) {
        return farmingLogService.createFarmingLog(userId, requestDto);
    }

    @PatchMapping("/{farmingLogId}")
    public FarmingLogResponseDTO updateFarmingLog(@AuthenticationPrincipal Long userId,
                                                  @PathVariable Long farmingLogId,
                                                  @RequestBody FarmingLogRequestDTO requestDto) {
        return farmingLogService.updateFarmingLog(userId, farmingLogId, requestDto);
    }

    @DeleteMapping("/{farmingLogId}")
    public void deleteFarmingLog(@AuthenticationPrincipal Long userId,
                                 @PathVariable Long farmingLogId) {
        farmingLogService.deleteFarmingLog(userId, farmingLogId);
    }
}
