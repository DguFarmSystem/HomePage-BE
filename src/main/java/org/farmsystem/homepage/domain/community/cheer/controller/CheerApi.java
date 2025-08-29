package org.farmsystem.homepage.domain.community.cheer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.farmsystem.homepage.domain.community.cheer.dto.request.CheerRequestDTO;
import org.farmsystem.homepage.domain.community.cheer.dto.response.CheerResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "응원 API", description = "응원 관련 API")
public interface CheerApi {

    @Operation(
            summary = "응원 목록 API",
            description = "응원 목록을 반환합니다.",
            security = @SecurityRequirement(name = "token"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CheerResponseDTO.class))
                    ))
    })
    ResponseEntity<SuccessResponse<?>> getAllCheer(Pageable pageable);

    @Operation(
            summary = "응원 등록 API",
            description = "응원을 등록합니다.",
            security = @SecurityRequirement(name = "token"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CheerResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "400", description = "자기 자신 응원"),
            @ApiResponse(responseCode = "404", description = "사용자 정보 없음")
    })
    ResponseEntity<SuccessResponse<?>> createCheer(@RequestBody @Valid CheerRequestDTO request);
}
