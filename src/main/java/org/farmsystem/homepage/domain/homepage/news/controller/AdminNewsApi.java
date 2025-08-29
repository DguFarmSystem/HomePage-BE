package org.farmsystem.homepage.domain.homepage.news.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.farmsystem.homepage.domain.homepage.news.dto.request.NewsRequestDTO;
import org.farmsystem.homepage.domain.homepage.news.dto.response.NewsDetailResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "[관리자] 소식 API", description = "관리자용 소식 관련 API")
public interface AdminNewsApi {

    @Operation(
            summary = "[관리자] 소식 생성",
            description = "새로운 소식을 등록합니다.",
            security = @SecurityRequirement(name = "token")
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NewsDetailResponseDTO.class)
                    )
            )
    })
    @PostMapping("/api/admin/news")
    ResponseEntity<SuccessResponse<?>> createNews(@RequestBody @Valid NewsRequestDTO request);

    @Operation(
            summary = "[관리자] 소식 수정",
            description = "기존 소식 제목과 내용을 수정합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NewsDetailResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 소식 ID")
    })
    @PutMapping("/api/admin/news/{newsId}")
    ResponseEntity<SuccessResponse<?>> updateNews(
            @PathVariable("newsId") Long newsId,
            @RequestBody @Valid NewsRequestDTO request);

    @Operation(
            summary = "[관리자] 소식 삭제",
            description = "ID를 통해 특정 소식을 삭제합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 소식 ID")
    })
    @DeleteMapping("/api/admin/news/{newsId}")
    ResponseEntity<SuccessResponse<?>>  deleteNews(@PathVariable("newsId") Long newsId);
}
