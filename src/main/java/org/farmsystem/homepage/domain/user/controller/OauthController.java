package org.farmsystem.homepage.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.dto.request.UserLoginRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserTokenResponseDTO;
import org.farmsystem.homepage.domain.user.service.OauthService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class OauthController {

    private final OauthService oauthService;

    // 소셜 로그인
    @PostMapping("/login")
    ResponseEntity<SuccessResponse<?>> socialLogin(@RequestBody UserLoginRequestDTO userLoginRequest) {
        UserTokenResponseDTO userToken = oauthService.socialLogin(userLoginRequest);
        return SuccessResponse.ok(userToken);
    }

}