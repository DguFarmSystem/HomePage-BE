package org.farmsystem.homepage.domain.farmingLog.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.farmingLog.service.FarmingLogLikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/farming-logs")
public class FarmingLogLikeController {

    private final FarmingLogLikeService farmingLogLikeService;

    @PostMapping("/{farmingLogId}/like")
    public void toggleLike(@AuthenticationPrincipal Long userId, @PathVariable Long farmingLogId) {
        farmingLogLikeService.toggleLike(userId, farmingLogId);
    }
}
