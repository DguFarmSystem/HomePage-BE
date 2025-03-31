package org.farmsystem.homepage.domain.notification.dto;

import lombok.Builder;
import org.farmsystem.homepage.domain.notification.entity.Notification;
import org.farmsystem.homepage.domain.notification.entity.NotificationType;

import java.time.LocalDateTime;

@Builder
public record NotificationResponseDTO(
        Long notificationId,
        NotificationType type,
        String title,
        String message,
        String targetUrl,
        boolean isRead,
        LocalDateTime createdAt
) {
    public static NotificationResponseDTO from(Notification notification) {
        return NotificationResponseDTO.builder()
                .notificationId(notification.getNotificationId())
                .type(notification.getType())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .targetUrl(notification.getTargetUrl())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
