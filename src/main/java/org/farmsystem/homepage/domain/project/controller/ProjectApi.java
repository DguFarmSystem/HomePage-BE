package org.farmsystem.homepage.domain.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.farmsystem.homepage.domain.project.dto.request.ProjectRequestDTO;
import org.farmsystem.homepage.domain.project.dto.response.ProjectResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "[파밍로그] 프로젝트 신청 API", description = "프로젝트 신청 및 조회")
@RequestMapping("/api/projects")
public interface ProjectApi {

    @Operation(
            summary = "프로젝트 신청",
            description = "유저가 프로젝트를 신청합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "신청 성공")
    })
    @PostMapping
    ResponseEntity<SuccessResponse<?>> applyForProject(
            @io.swagger.v3.oas.annotations.Parameter(hidden = true) Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "신청할 프로젝트 정보",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProjectRequestDTO.class))
            )
            @Valid @RequestBody ProjectRequestDTO request
    );


    @Operation(
            summary = "내가 신청한 프로젝트 목록",
            description = "유저가 신청한 프로젝트 목록을 조회합니다.",
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
    @GetMapping("/my")
    ResponseEntity<SuccessResponse<?>> getMyProjects(
            @io.swagger.v3.oas.annotations.Parameter(hidden = true) Long userId
    );
}
