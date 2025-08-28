package org.farmsystem.homepage.domain.blog.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.farmsystem.homepage.domain.blog.dto.request.BlogRequestDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "[관리자] 블로그", description = "관리자 전용 블로그 API")
public interface AdminBlogApi {

    @Operation(summary = "블로그 승인", description = "승인 대기 중인 특정 블로그를 승인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "승인 성공"),
            @ApiResponse(responseCode = "404", description = "블로그 또는 관리자 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 승인/거절된 블로그",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class)))
    })
    ResponseEntity<SuccessResponse<?>> approveBlog(
            @Parameter(description = "승인할 블로그 ID") @PathVariable Long blogId,
            @Parameter(description = "관리자 ID (인증 토큰)") @RequestParam Long userId
    );

    @Operation(summary = "블로그 거절", description = "승인 대기 중인 특정 블로그를 거절합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "거절 성공"),
            @ApiResponse(responseCode = "404", description = "블로그 또는 관리자 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 승인/거절된 블로그",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class)))
    })
    ResponseEntity<SuccessResponse<?>> rejectBlog(
            @Parameter(description = "거절할 블로그 ID") @PathVariable Long blogId,
            @Parameter(description = "관리자 ID (인증 토큰)") @RequestParam Long userId
    );

    @Operation(summary = "승인 대기 중인 블로그 목록 조회", description = "관리자가 승인/거절할 블로그 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    ResponseEntity<SuccessResponse<?>> getPendingBlogs();

    @Operation(summary = "관리자 - 블로그 직접 생성", description = "관리자가 블로그를 직접 생성하며, 바로 승인 상태가 됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "404", description = "관리자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 등록된 블로그",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class)))
    })
    ResponseEntity<SuccessResponse<?>> createBlog(
            @RequestBody @Valid BlogRequestDTO request,
            @Parameter(description = "관리자 ID (인증 토큰)") @RequestParam Long userId
    );
}