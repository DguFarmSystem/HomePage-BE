package org.farmsystem.homepage.domain.homepage.apply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.homepage.apply.dto.response.ApplyListResponseDTO;
import org.farmsystem.homepage.domain.homepage.apply.dto.response.LoadApplyResponseDTO;
import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "지원 관리자 API", description = "지원 관련 관리자 API")
public interface AdminApplyApi {

    @Operation(
            summary = "지원서 목록 API",
            description = "제출 완료된 지원서 목록을 반환합니다. track이 null이면 모든 트랙의 지원서를 반환합니다.",
            security = @SecurityRequirement(name = "token"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApplyListResponseDTO.class))
                    ))
    })
    ResponseEntity<SuccessResponse<?>> getApplyList(@RequestParam(required = false) Track track);

    @Operation(
            summary = "지원서 상세 API",
            description = "해당 지원서 번호의 지원서 상세 내용을 반환합니다.",
            security = @SecurityRequirement(name = "token"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoadApplyResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "지원서 없음")
    })
    ResponseEntity<SuccessResponse<?>> getApply(@PathVariable Long applyId);
}
