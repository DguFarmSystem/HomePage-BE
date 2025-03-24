package org.farmsystem.homepage.domain.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.dto.request.UserUpdateRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.UserVerifyRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.*;
import org.farmsystem.homepage.domain.user.service.UserRankingService;
import org.farmsystem.homepage.domain.user.service.UserService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController implements UserApi {
    private final UserService userService;
    private final UserRankingService userRankingService;

    // 사용자 회원 인증 API
    @PostMapping("/verify")
    public ResponseEntity<SuccessResponse<?>> verifyUser(@RequestBody @Valid UserVerifyRequestDTO userVerifyRequest) {
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
    @PatchMapping(value = "/mypage")
    public ResponseEntity<SuccessResponse<?>> updateUserInfo(@AuthenticationPrincipal Long userId,
                                                             @RequestBody UserUpdateRequestDTO userInfoRequest) {
        UserInfoResponseDTO updatedUserInfo = userService.updateUserInfo(userId, userInfoRequest);
        return SuccessResponse.ok(updatedUserInfo);
    }

    //다른 사용자 정보 조회 API
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<?>> getOtherUserInfo(@PathVariable Long userId) {
        OtherUserInfoResponseDTO userInfo = userService.getOtherUserInfo(userId);
        return SuccessResponse.ok(userInfo);
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

    // 사용자 랭킹
    @GetMapping("/ranking")
    public ResponseEntity<SuccessResponse<?>> getUserRanking(@AuthenticationPrincipal Long userId) {
        UserRankListResponseDTO userRankList = userRankingService.getDailyRanking(userId);
        return SuccessResponse.ok(userRankList);
    }
}
