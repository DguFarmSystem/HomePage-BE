package org.farmsystem.homepage.domain.project.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.project.dto.request.ProjectRequestDTO;
import org.farmsystem.homepage.domain.project.dto.response.ProjectResponseDTO;
import org.farmsystem.homepage.domain.project.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 프로젝트 등록 신청
     */
    @PostMapping
    public ResponseEntity<Void> applyForProject(
            @AuthenticationPrincipal Long userId,
            @RequestBody ProjectRequestDTO request
    ) {
        projectService.applyForProject(userId, request);
        return ResponseEntity.ok().build();
    }

    /**
     * 승인된 프로젝트 목록 조회
     */
    @GetMapping("/approved")
    public ResponseEntity<List<ProjectResponseDTO>> getApprovedProjects() {
        return ResponseEntity.ok(projectService.getApprovedProjects());
    }

    /**
     * 사용자가 신청한 프로젝트 목록 조회 (모든 정보 반환)
     */
//    @GetMapping("/my")
//    public ResponseEntity<List<ProjectResponseDTO>> getMyProjects(@AuthenticationPrincipal Long userId) {
//        return ResponseEntity.ok(projectService.getMyProjects(userId));
//    }

    /**
     * 홍보 페이지에서 승인된 프로젝트 페이징 조회
     */
    @GetMapping("/page")
    public ResponseEntity<Page<ProjectResponseDTO>> getApprovedProjectsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(projectService.getApprovedProjectsPaged(page, size));
    }

    /**
     * 특정 기수의 승인된 프로젝트 조회 (페이징)
     */
    @GetMapping("/generation/{generation}")
    public ResponseEntity<Page<ProjectResponseDTO>> getProjectsByGeneration(
            @PathVariable Integer generation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(projectService.getProjectsByGeneration(generation, page, size));
    }
}
