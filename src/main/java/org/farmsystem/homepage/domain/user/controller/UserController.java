package org.farmsystem.homepage.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.dto.request.UserInfoUpdateRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.UserVerifyRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserInfoResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserVerifyResponseDTO;
import org.farmsystem.homepage.domain.user.service.UserService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController implements UserApi {
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
    @PatchMapping(value = "/mypage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<?>> updateUserInfo(@AuthenticationPrincipal Long userId,
                                                             @ModelAttribute UserInfoUpdateRequestDTO userInfoRequest) {
        UserInfoResponseDTO updatedUserInfo = userService.updateUserInfo(userId, userInfoRequest);
        return SuccessResponse.ok(updatedUserInfo);
    }
}
