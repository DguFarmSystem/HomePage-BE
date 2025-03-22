package org.farmsystem.homepage.domain.project.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.project.dto.response.ProjectPageResultDTO;
import org.farmsystem.homepage.domain.project.dto.response.ProjectResponseDTO;
import org.farmsystem.homepage.domain.project.dto.response.ProjectSimpleResponseDTO;
import org.farmsystem.homepage.domain.project.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home/projects")
@RequiredArgsConstructor
public class HomeProjectController {

    private final ProjectService projectService;

    /**
     * 기수/트랙 필터링 조회 (페이징)
     */
    @GetMapping("/filter")
    public ResponseEntity<ProjectPageResultDTO<ProjectSimpleResponseDTO>> getFilteredProjects(
            @RequestParam(required = false) Integer generation,
            @RequestParam(required = false) String track,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Track trackEnum = (track != null) ? Track.from(track) : null;
        Page<ProjectSimpleResponseDTO> projectPage = projectService.getFilteredProjects(generation, trackEnum, page, size);
        return ResponseEntity.ok(ProjectPageResultDTO.of(projectPage, PageRequest.of(page, size)));
    }


    /*
    프로젝트 단일 조회
     */
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectDetail(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProjectDetail(projectId));
    }
}
