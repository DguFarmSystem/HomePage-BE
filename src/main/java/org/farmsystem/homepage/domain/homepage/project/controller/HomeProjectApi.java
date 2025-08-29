package org.farmsystem.homepage.domain.homepage.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.homepage.project.dto.response.ProjectPageResultDTO;
import org.farmsystem.homepage.domain.homepage.project.dto.response.ProjectResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "[홍보 페이지] 프로젝트 조회 API", description = "기수 및 트랙 필터링, 단일 프로젝트 상세 조회")
@RequestMapping("/api/home/projects")
public interface HomeProjectApi {

    @Operation(summary = "필터링된 프로젝트 조회", description = "기수 및 트랙으로 필터링된 프로젝트를 페이징 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProjectPageResultDTO.class)
                    )
            )
    })
    @GetMapping("/filter")
    ResponseEntity<SuccessResponse<?>> getFilteredProjects(
            @RequestParam(required = false) Integer generation,
            @RequestParam(required = false) String track,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "단일 프로젝트 상세 조회", description = "projectId로 프로젝트 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProjectResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로젝트 ID")
    })
    @GetMapping("/{projectId}")
    ResponseEntity<SuccessResponse<?>> getProjectDetail(@PathVariable Long projectId);
}
