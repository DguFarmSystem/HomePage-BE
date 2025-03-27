package org.farmsystem.homepage.domain.notification.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.notification.service.NotificationService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController implements NotificationApi {

    private final NotificationService notificationService;

    // SSE 연결 생성
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@AuthenticationPrincipal Long userId) {
        return notificationService.subscribe(userId);
    }

    // 알림 목록 조회
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getNotifications(@AuthenticationPrincipal Long userId) {
        return SuccessResponse.ok(notificationService.getNotifications(userId));
    }

    // 알림 읽음 처리
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<SuccessResponse<?>> readNotifications(@PathVariable Long notificationId, @AuthenticationPrincipal Long userId) {
        notificationService.markNotificationAsRead(notificationId, userId);
        return SuccessResponse.noContent();
    }
}
