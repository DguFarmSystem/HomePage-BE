package org.farmsystem.homepage.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.user.dto.request.UserInfoUpdateRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.UserVerifyRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserInfoResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserVerifyResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

@Tag(name = "사용자 API", description = "사용자 관련 API")
public interface UserApi {

    @Operation(
            summary = "사용자 회원 인증 API",
            description = "주어진 학번을 통해 인증된 사용자(팜 회원)인지 확인하고 사용자 이름(확인용)을 반환하는 API입니다. "
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 인증 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserVerifyResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "401", description = "사용자 인증 실패(합격자 명단에 없는 사용자인 경우)")
    })
    ResponseEntity<SuccessResponse<?>> verifyUser(@RequestBody UserVerifyRequestDTO userVerifyRequest);


    @Operation(
            summary = "마이페이지 사용자 정보 조회",
            description = "토큰 필요. 사용자의 정보를 조회하는 API입니다. ",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "사용자 정보 없음")
    })
    ResponseEntity<SuccessResponse<?>> getUserInfo(Long userId);

    @Operation(
            summary = "마이페이지 사용자 정보 수정",
            description = "토큰 필요. 사용자의 정보를 수정하는 API입니다.  \n" +
                    "multipart/form-data형식이고 phoneNumber, major, profileImage 중 하나 이상 수정 요청 가능합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "500", description = "프로필 이미지 업로드 실패")
    })
    ResponseEntity<SuccessResponse<?>> updateUserInfo(
            Long userId,
            @ModelAttribute UserInfoUpdateRequestDTO userInfoRequest
    );
}
