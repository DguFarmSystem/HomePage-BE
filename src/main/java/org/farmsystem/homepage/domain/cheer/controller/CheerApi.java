package org.farmsystem.homepage.domain.cheer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.apply.dto.response.ApplyListResponseDTO;
import org.farmsystem.homepage.domain.apply.dto.response.LoadApplyResponseDTO;
import org.farmsystem.homepage.domain.cheer.dto.response.CheerResponseDTO;
import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<SuccessResponse<?>> getAllCheer(Pageable pageable);
}
