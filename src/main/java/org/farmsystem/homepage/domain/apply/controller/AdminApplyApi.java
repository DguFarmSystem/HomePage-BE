package org.farmsystem.homepage.domain.apply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.apply.dto.response.ApplyListResponseDTO;
import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "지원 관리자 API", description = "지원 관련 관리자 API")
public interface AdminApplyApi {

    @Operation(
            summary = "지원서 확인 API",
            description = "제출 완료된 지원서를 반환하는 API입니다. track이 null이면 모든 트랙의 지원서를 반환합니다.",
            security = @SecurityRequirement(name = "token"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지원서 확인하기 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApplyListResponseDTO.class))
                    ))
    })
    ResponseEntity<SuccessResponse<?>> getApplyList(@RequestParam(required = false) Track track);
}
