package org.farmsystem.homepage.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.dto.request.AdminUserUpdateRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserInfoResponseDTO;
import org.farmsystem.homepage.domain.user.service.UserService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
@RestController
public class AdminUserController {

    private final UserService userService;


    // 사용자 정보 수정 API
    @PatchMapping("/{userId}")
    public ResponseEntity<SuccessResponse<?>> updateUser(@PathVariable Long userId, @RequestBody AdminUserUpdateRequestDTO adminUserUpdateRequest) {
        UserInfoResponseDTO updatedUser = userService.updateUserByAdmin(userId, adminUserUpdateRequest);
        return SuccessResponse.ok(updatedUser);
    }

    // 사용자 삭제 API
    @DeleteMapping("/{userId}")
    public ResponseEntity<SuccessResponse<?>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return SuccessResponse.ok(null);
    }

}
