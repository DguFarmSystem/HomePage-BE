package org.farmsystem.homepage.domain.homepage.project.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.homepage.project.dto.request.ProjectRequestDTO;
import org.farmsystem.homepage.domain.homepage.project.service.ProjectService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController implements ProjectApi{

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> applyForProject(
            @AuthenticationPrincipal Long userId,
            @RequestBody ProjectRequestDTO request
    ) {
        projectService.applyForProject(userId, request);
        return SuccessResponse.noContent();
    }

    @GetMapping("/my")
    public ResponseEntity<SuccessResponse<?>> getMyProjects(@AuthenticationPrincipal Long userId) {
        return SuccessResponse.ok(projectService.getMyProjects(userId));
    }
}


