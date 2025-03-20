package org.farmsystem.homepage.domain.apply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.apply.dto.request.PassedApplyRegisterRequestDTO;
import org.farmsystem.homepage.domain.apply.dto.response.PassedApplyRegisterResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;


@Tag(name = "[관리자] 합격자 API", description = "관리자용 합격자 관리 API")
public interface AdminPassedApplyApi {

    @Operation(
            summary = "합격자(회원) 개별 등록 API",
            description = "관리자가 사용자 스스로 회원가입할 수 있도록 회원 인증 정보를 등록하는 API입니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 등록 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PassedApplyRegisterResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "400", description = "이미 등록된 회원")
    })
    ResponseEntity<SuccessResponse<?>> registerUser(
            @RequestBody PassedApplyRegisterRequestDTO adminUserRegisterRequest
    );
}
