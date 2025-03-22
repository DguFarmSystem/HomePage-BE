package org.farmsystem.homepage.domain.notification.event;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.notification.entity.NotificationType;
import org.farmsystem.homepage.domain.notification.service.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

//    @EventListener
//    public void handleCheerReceiveEvent(CheerReceiveEvent event) {
//        // 제목, 메시지 등은 추후 변경
//        String title = "새 응원 알림";
//        String message = event.cheererName() + "님이 " + event.cheeredName() + "님을 응원했어요!";
//        String targetUrl = "/cheer/" + event.cheerId();
//
//        // SSE를 통해 해당 사용자에게 알림 전송
//        notificationService.sendNotification(event.cheeredId(), title, message, NotificationType.CHEER,  targetUrl);
//    }
}
