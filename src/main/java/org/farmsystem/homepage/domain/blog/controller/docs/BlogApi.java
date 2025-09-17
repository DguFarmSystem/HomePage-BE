package org.farmsystem.homepage.domain.blog.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.blog.dto.request.BlogRequestDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "블로그", description = "블로그 API")
public interface BlogApi {

    @Operation(summary = "블로그 신청", description = "회원 ID와 함께 블로그를 신청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "블로그 신청 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 신청된 블로그",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class)))
    })
    ResponseEntity<SuccessResponse<?>> applyForBlog(@RequestBody BlogRequestDTO request, @RequestParam Long userId);

    @Operation(summary = "승인된 블로그 목록 조회", description = "승인 상태인 블로그 전체 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    ResponseEntity<SuccessResponse<?>> getApprovedBlogs();

    @Operation(summary = "내가 신청한 블로그 목록 조회", description = "특정 회원이 신청한 블로그 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    ResponseEntity<SuccessResponse<?>> getMyBlogs(@RequestParam Long userId);

    @Operation(summary = "최신 승인 블로그 페이징 조회", description = "최신순으로 승인된 블로그를 페이징하여 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    ResponseEntity<SuccessResponse<?>> getApprovedBlogsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );
}
