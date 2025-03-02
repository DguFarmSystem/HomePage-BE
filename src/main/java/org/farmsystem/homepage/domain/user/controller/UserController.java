package org.farmsystem.homepage.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.dto.request.UserInfoUpdateRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.UserVerifyRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserInfoResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserVerifyResponseDTO;
import org.farmsystem.homepage.domain.user.service.UserService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;

    // 사용자 회원 인증 API
    @PostMapping("/verify")
    public ResponseEntity<SuccessResponse<?>> verifyUser(@RequestBody UserVerifyRequestDTO userVerifyRequest) {
        UserVerifyResponseDTO userVerify = userService.verifyUser(userVerifyRequest);
        return SuccessResponse.ok(userVerify);
    }

    // 마이페이지 사용자 정보 조회 API
    @GetMapping("/mypage")
    public ResponseEntity<SuccessResponse<?>> getUserInfo(@AuthenticationPrincipal Long userId) {
        UserInfoResponseDTO userInfo = userService.getUserInfo(userId);
        return SuccessResponse.ok(userInfo);
    }

    // 사용자 정보 수정 API
    @PutMapping("/mypage")
    public ResponseEntity<SuccessResponse<?>> updateUserInfo(@AuthenticationPrincipal Long userId,
                                                             @ModelAttribute UserInfoUpdateRequestDTO userInfoRequest) throws IOException {
        UserInfoResponseDTO updatedUserInfo = userService.updateUserInfo(userId, userInfoRequest);
        return SuccessResponse.ok(updatedUserInfo);
    }

    // 사용자 검색 API
    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<?>> searchUser(@RequestParam String query) {
        return SuccessResponse.ok(userService.searchUser(query));
    }

    // 사용자 검색 자동완성 API
    @GetMapping("/suggest")
    public ResponseEntity<SuccessResponse<?>> searchUserSuggest(@RequestParam String query) {
        return SuccessResponse.ok(userService.searchUserSuggest(query));
    }
}
