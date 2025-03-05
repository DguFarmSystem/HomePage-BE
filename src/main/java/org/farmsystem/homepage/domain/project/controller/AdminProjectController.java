package org.farmsystem.homepage.domain.project.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.project.dto.response.ProjectResponseDTO;
import org.farmsystem.homepage.domain.project.dto.response.ProjectApprovalResponseDTO;
import org.farmsystem.homepage.domain.project.service.AdminProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/projects")
@RequiredArgsConstructor
public class AdminProjectController {

    private final AdminProjectService adminProjectService;

    /**
     * 관리자 - 프로젝트 승인
     */
    @PatchMapping("/{projectId}/approve")
    public ResponseEntity<ProjectApprovalResponseDTO> approveProject(
            @PathVariable Long projectId,
            @AuthenticationPrincipal Long adminUserId
    ) {
        return ResponseEntity.ok(adminProjectService.approveProject(projectId, adminUserId));
    }

    /**
     * 관리자 - 프로젝트 거절
     */
    @PatchMapping("/{projectId}/reject")
    public ResponseEntity<ProjectApprovalResponseDTO> rejectProject(
            @PathVariable Long projectId,
            @AuthenticationPrincipal Long adminUserId
    ) {
        return ResponseEntity.ok(adminProjectService.rejectProject(projectId, adminUserId));
    }

    /**
     * 관리자 - 승인 대기 중인 프로젝트 목록 조회
     */
    @GetMapping("/pending")
    public ResponseEntity<List<ProjectResponseDTO>> getPendingProjects() {
        return ResponseEntity.ok(adminProjectService.getPendingProjects());
    }
}
