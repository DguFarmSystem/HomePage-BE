package org.farmsystem.homepage.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.user.dto.request.UserUpdateRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.UserVerifyRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.OtherUserInfoResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserInfoResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserRankListResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserVerifyResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "사용자 API", description = "사용자 관련 API")
public interface UserApi {

    @Operation(
            summary = "사용자 회원 인증",
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
                    "profileImage, phoneNumber, not ionAccount,githubAccount 중 하나 이상 수정 요청 가능합니다.  \n" +
                    "profileImage(프로필 이미지)는 <프로필 사진 업로드용 Presigned URL 생성 API>를 통해 S3 업로드 가능하며 수정 요청시 해당 객체 URL(String)을 전달해야 합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "500", description = "사용자 정보 수정 실패")
    })
    ResponseEntity<SuccessResponse<?>> updateUserInfo(
            Long userId,
            @RequestBody UserUpdateRequestDTO userInfoRequest
    );

    @Operation(
            summary = "다른 사용자 정보 조회",
            description = "토큰 필요. 사용자가 다른 사용자의 정보를 조회하는 API입니다. ",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "다른 사용자 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OtherUserInfoResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "사용자 정보 없음")
    })
    ResponseEntity<SuccessResponse<?>> getOtherUserInfo(Long userId);

    @Operation(
            summary = "출석하기",
            description = "사용자가 출석을 하는 API입니다.  \n" +
                    " 출석 후 1개의 씨앗이 적립됩니다. 출석 씨앗 적립은 하루에 한 번만 가능합니다. ",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "출석 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "사용자 정보 없음")
    })
    @PostMapping("/attendance")
    ResponseEntity<SuccessResponse<?>> attend(Long userId);

    @Operation(
            summary = "사용자 랭킹 조회",
            description = "사용자의 랭킹을 조회하는 API입니다. 사용자 자신과 전체 랭킹을 반환합니다.  \n" +
                    "랭킹은 0시 기준으로 갱신됩니다. ",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "랭킹 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRankListResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "사용자 정보 없음")
    })
    @GetMapping("/ranking")
    ResponseEntity<SuccessResponse<?>> getUserRanking(Long userId);
}

