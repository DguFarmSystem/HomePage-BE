package org.farmsystem.homepage.domain.notification.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.notification.service.NotificationService;
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
}
