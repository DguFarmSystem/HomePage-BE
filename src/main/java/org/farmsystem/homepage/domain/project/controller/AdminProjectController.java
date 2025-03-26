package org.farmsystem.homepage.domain.project.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.project.service.AdminProjectService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/projects")
@RequiredArgsConstructor
public class AdminProjectController implements AdminProjectApi{

    private final AdminProjectService adminProjectService;

    @PatchMapping("/{projectId}/approve")
    public ResponseEntity<SuccessResponse<?>> approveProject(
            @PathVariable Long projectId,
            @AuthenticationPrincipal Long adminUserId
    ) {
        return SuccessResponse.ok(adminProjectService.approveProject(projectId, adminUserId));
    }

    @PatchMapping("/{projectId}/reject")
    public ResponseEntity<SuccessResponse<?>> rejectProject(
            @PathVariable Long projectId,
            @AuthenticationPrincipal Long adminUserId
    ) {
        return SuccessResponse.ok(adminProjectService.rejectProject(projectId, adminUserId));
    }

    @GetMapping("/pending")
    public ResponseEntity<SuccessResponse<?>> getPendingProjects() {
        return SuccessResponse.ok(adminProjectService.getPendingProjects());
    }
}
