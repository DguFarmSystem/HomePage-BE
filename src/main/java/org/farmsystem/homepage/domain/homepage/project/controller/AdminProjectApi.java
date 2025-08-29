package org.farmsystem.homepage.domain.homepage.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.homepage.project.dto.response.ProjectApprovalResponseDTO;
import org.farmsystem.homepage.domain.homepage.project.dto.response.ProjectResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "[관리자] 프로젝트 승인/거절 API", description = "관리자가 프로젝트를 승인/거절/조회합니다.")
@RequestMapping("/api/admin/projects")
public interface AdminProjectApi {

    @Operation(
            summary = "프로젝트 승인",
            description = "관리자가 프로젝트를 승인합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "승인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProjectApprovalResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로젝트 ID")
    })
    @PatchMapping("/{projectId}/approve")
    ResponseEntity<SuccessResponse<?>> approveProject(
            @Parameter(hidden = true) Long adminUserId,
            @PathVariable Long projectId
    );

    @Operation(
            summary = "프로젝트 거절",
            description = "관리자가 프로젝트를 거절합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "거절 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProjectApprovalResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 프로젝트 ID")
    })
    @PatchMapping("/{projectId}/reject")
    ResponseEntity<SuccessResponse<?>> rejectProject(
            @Parameter(hidden = true) Long adminUserId,
            @PathVariable Long projectId
    );

    @Operation(
            summary = "승인 대기 중인 프로젝트 목록",
            description = "관리자가 아직 승인되지 않은 프로젝트들을 조회합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProjectResponseDTO.class))
                    )
            )
    })
    @GetMapping("/pending")
    ResponseEntity<SuccessResponse<?>> getPendingProjects();
}
