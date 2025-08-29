package org.farmsystem.homepage.domain.community.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.community.user.dto.request.UserLoginRequestDTO;
import org.farmsystem.homepage.domain.community.user.dto.request.UserTokenRequestDTO;
import org.farmsystem.homepage.domain.community.user.dto.response.UserTokenResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "인증 API", description = "인증 관련 API")
public interface AuthApi {

    @Operation(summary = "임시 토큰 발급",
            description = "사용자에게 임시 토큰을 발급하는 API입니다.(소셜 로그인 없이 테스트 토큰 발급용)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "임시 토큰 발급 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserTokenResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    ResponseEntity<SuccessResponse<?>> getTempToken(
            @Parameter(description = "토큰을 발급 받을 사용자의 ID") @PathVariable Long userId
    );

    @Operation(summary = "소셜 로그인(회원 가입 포함)",
            description = "소셜 로그인 정보를 받아 엑세스, 리프레시 토큰을 발급하는 API 입니다.  \n"  +
                    "소셜로그인 다른 계정으로 이미 가입한 경우에는 회원 가입이 불가능합니다. (1인 1계정)  \n" +
                    "신규 가입자의 경우 회원 인증 받을 때 사용한 학번을 입력받아 회원가입을 진행합니다. (기존 가입자는 학번 없이 로그인 처리)  \n" +
                    "code(인가코드)는 일회성이기때문에 같은 인가코드에 대해서 한 번씩만 호출 가능합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "소셜 로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserTokenResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "사용자 회원 인증 실패"),
            @ApiResponse(responseCode = "409", description = "이미 가입된 사용자"),
            @ApiResponse(responseCode = "500", description = "Oauth 토큰 발급 실패"),
            @ApiResponse(responseCode = "500", description = "Oauth 사용자 정보 조회 실패"),
    })
    ResponseEntity<SuccessResponse<?>> socialLogin(
            @RequestBody UserLoginRequestDTO userLoginRequest
    );

    @Operation(summary = "로그아웃",
            description = "토큰 필요. 현재의 리프레시 토큰을 무효화하여 로그아웃하는 API입니다..",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    ResponseEntity<SuccessResponse<?>> logout(Long userId);


    @Operation(summary = "엑세스 토큰 재발급",
            description = "엑세스 토큰 만료시 리프레쉬 토큰을 사용해 새로운 액세스 토큰을 재발급하는 API입니다.  \n" +
                    "리프레시 토큰 만료 시에는 사용자 재로그인이 필요합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserTokenResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "401",  description = "리프레쉬 토큰 만료"),
            @ApiResponse(responseCode = "401",  description = "유효하지 않은 리프레쉬 토큰"),
            @ApiResponse(responseCode = "401",  description = "저장된 리프레쉬 토큰 불일치")
    })
    ResponseEntity<SuccessResponse<?>> reissueToken(
            @RequestBody UserTokenRequestDTO uerTokenRequest
    );
}
