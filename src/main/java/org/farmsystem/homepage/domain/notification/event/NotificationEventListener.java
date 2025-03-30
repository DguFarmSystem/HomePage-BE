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

    @EventListener
    public void handleCheerReceiveEvent(CheerReceiveEvent event) {
        String title = "새 응원 알림";
        String message = event.CheererGeneration().toString() + " " + event.CheererTrack().toString() + " " + event.CheererName() + "님이 응원을 보냈습니다.";
        String targetUrl = "/cheer/" + event.cheerId();

        // SSE를 통해 해당 사용자에게 알림 전송
        notificationService.sendNotification(event.cheeredId(), title, message, NotificationType.CHEER, targetUrl);
    }
}
