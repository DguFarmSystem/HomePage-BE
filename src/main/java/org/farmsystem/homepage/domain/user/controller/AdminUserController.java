package org.farmsystem.homepage.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.dto.request.AdminUserRegisterRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.AdminUserSearchRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.AdminUserUpdateRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserInfoResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.PagingUserListResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserRegisterResponseDTO;
import org.farmsystem.homepage.domain.user.service.UserService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
@RestController
public class AdminUserController implements AdminUserApi {

    private final UserService userService;

    // [관리자] 회원 정보 등록(회원인증 및 가입 가능하도록)
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> registerUser(@RequestBody AdminUserRegisterRequestDTO adminUserRegisterRequest) {
        UserRegisterResponseDTO registedUser= userService.registerUser(adminUserRegisterRequest);
        return SuccessResponse.ok(registedUser);
    }

    // [관리자] 사용자 정보 수정 API
    @PatchMapping("/{userId}")
    public ResponseEntity<SuccessResponse<?>> updateUser(@PathVariable Long userId, @RequestBody AdminUserUpdateRequestDTO adminUserUpdateRequest) {
        UserInfoResponseDTO updatedUser = userService.updateUserByAdmin(userId, adminUserUpdateRequest);
        return SuccessResponse.ok(updatedUser);
    }

    // [관리자] 사용자 삭제 API
    @DeleteMapping("/{userId}")
    public ResponseEntity<SuccessResponse<?>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return SuccessResponse.ok(null);
    }

    // [관리자] 사용자 정보 전체 조회 API
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getAllUsers(Pageable pageable, @ModelAttribute AdminUserSearchRequestDTO query) {
        PagingUserListResponseDTO users = userService.getAllUsers(pageable, query);
        return SuccessResponse.ok(users);
    }

    // [관리자] 삭제된 사용자 조회
    @GetMapping("/deleted")
    public ResponseEntity<SuccessResponse<?>> getDeletedUsers(Pageable pageable) {
        PagingUserListResponseDTO users = userService.getDeletedUsers(pageable);
        return SuccessResponse.ok(users);
    }


}
