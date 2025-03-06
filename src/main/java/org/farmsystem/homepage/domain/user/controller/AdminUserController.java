package org.farmsystem.homepage.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.service.UserService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
@RestController
public class AdminUserController {

    private final UserService userService;

    // 사용자 삭제 API
    @DeleteMapping("/{userId}")
    public ResponseEntity<SuccessResponse<?>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return SuccessResponse.noContent();
    }

}
