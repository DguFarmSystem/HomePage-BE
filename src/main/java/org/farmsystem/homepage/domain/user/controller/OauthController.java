package org.farmsystem.homepage.domain.user.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.dto.request.UserLoginRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.UserTokenRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserTokenResponseDTO;
import org.farmsystem.homepage.domain.user.service.OauthService;
import org.farmsystem.homepage.domain.user.service.TokenService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class OauthController {

    private final OauthService oauthService;
    private final TokenService tokenService;

    // 임시 토큰 발급 API. 추후 로그인 기능이 완성되면 삭제할 예정
    @PostMapping("/token/{userId}")
    public ResponseEntity<SuccessResponse<?>> getToken(@PathVariable Long userId) {
        UserTokenResponseDTO userToken= tokenService.issueTempToken(userId);
        return SuccessResponse.ok(userToken);
    }

    // 소셜 로그인
    @PostMapping("/login")
    ResponseEntity<SuccessResponse<?>> socialLogin(@RequestBody UserLoginRequestDTO userLoginRequest) {
        UserTokenResponseDTO userToken = oauthService.socialLogin(userLoginRequest);
        return SuccessResponse.ok(userToken);
    }

    // 로그아웃
    @PostMapping("/logout")
    ResponseEntity<SuccessResponse<?>> logout(@AuthenticationPrincipal Long userId) {
        tokenService.logout(userId);
        return SuccessResponse.ok(null);
    }

    // 리프레쉬 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<SuccessResponse<?>> reissueToken(@RequestBody UserTokenRequestDTO uerTokenRequest) throws JsonProcessingException {
        UserTokenResponseDTO userToken = tokenService.reissue(uerTokenRequest);
        return SuccessResponse.ok(userToken);
    }

}