package org.farmsystem.homepage.domain.homepage.project.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.homepage.project.dto.response.ProjectPageResultDTO;
import org.farmsystem.homepage.domain.homepage.project.dto.response.ProjectSimpleResponseDTO;
import org.farmsystem.homepage.domain.homepage.project.service.ProjectService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home/projects")
@RequiredArgsConstructor
public class HomeProjectController implements HomeProjectApi{

    private final ProjectService projectService;

    @GetMapping("/filter")
    public ResponseEntity<SuccessResponse<?>> getFilteredProjects(
            @RequestParam(required = false) Integer generation,
            @RequestParam(required = false) String track,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Track trackEnum = (track != null) ? Track.from(track) : null;
        Page<ProjectSimpleResponseDTO> projectPage = projectService.getFilteredProjects(generation, trackEnum, page, size);
        return SuccessResponse.ok(ProjectPageResultDTO.of(projectPage, PageRequest.of(page, size)));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<SuccessResponse<?>> getProjectDetail(@PathVariable Long projectId) {
        return SuccessResponse.ok(projectService.getProjectDetail(projectId));
    }
}
