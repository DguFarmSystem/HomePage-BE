package org.farmsystem.homepage.domain.project.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.project.dto.request.ProjectRequestDTO;
import org.farmsystem.homepage.domain.project.dto.response.ProjectResponseDTO;
import org.farmsystem.homepage.domain.project.service.ProjectService;
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
     * 파밍로그 - 프로젝트 등록 신청
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
     * 파밍로그 - 사용자가 신청한 프로젝트 목록 조회
     */
    @GetMapping("/my")
    public ResponseEntity<List<ProjectResponseDTO>> getMyProjects(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(projectService.getMyProjects(userId));
    }


}

