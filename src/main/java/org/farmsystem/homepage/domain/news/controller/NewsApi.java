package org.farmsystem.homepage.domain.news.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import org.farmsystem.homepage.domain.news.dto.response.NewsResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "소식 API", description = "소식 관련 API")
@RequestMapping("/api/news")
public interface NewsApi {

    @Operation(summary = "전체 소식 조회", description = "등록된 모든 소식을 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NewsResponseDTO.class))
                    )
            )
    })
    @GetMapping
    ResponseEntity<SuccessResponse<?>> getAllNews();

    @Operation(summary = "단일 소식 조회", description = "ID를 통해 특정 소식을 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NewsResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 소식 ID")
    })
    @GetMapping("/{newsId}")
    ResponseEntity<SuccessResponse<?>> getNewsById(@PathVariable("newsId") Long newsId);
}
