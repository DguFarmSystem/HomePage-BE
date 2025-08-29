package org.farmsystem.homepage.domain.community.notification.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.community.notification.dto.NotificationResponseDTO;
import org.farmsystem.homepage.domain.community.notification.entity.Notification;
import org.farmsystem.homepage.domain.community.notification.entity.NotificationType;
import org.farmsystem.homepage.domain.community.notification.repository.NotificationRepository;
import org.farmsystem.homepage.domain.community.user.entity.User;
import org.farmsystem.homepage.domain.community.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    // SSE 클라이언트 연결 관리 저장소
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    // 구독 요청 시 새로운 SseEmitter를 생성하고 등록
    public SseEmitter subscribe(Long userId) {
        // 타임아웃 시간 설정 (예: 1시간)
        SseEmitter emitter = new SseEmitter(3600000L);
        emitters.put(userId, emitter);

        // 연결 종료, 타임아웃 시 클라이언트 제거
        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(emitter::complete);
        try {
            // 초기 이벤트를 전송하여 연결이 성공적으로 수립되었음을 클라이언트에 알림
            emitter.send(SseEmitter.event().name("INIT").data("connected"));
        } catch (IOException e) {
            emitter.completeWithError(e);
            emitters.remove(userId);
        }
        return emitter;
    }

    @Transactional
    public void sendNotification(Long userId, String title, String message, NotificationType type, String targetUrl) {
        // 해당 사용자에 연결된 SSEEmitter 가져오기
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(Map.of("title", title, "message", message)));
            } catch (IOException e) {
                // 오류 발생 시 연결 종료 및 제거
                emitter.completeWithError(e);
                emitters.remove(userId);
            }
        }
        // 알림 기록 저장
        User receivedUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
        Notification notification = Notification.builder()
                .user(receivedUser)
                .type(type)
                .title(title)
                .message(message)
                .targetUrl(targetUrl)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }

    public List<NotificationResponseDTO> getNotifications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
        List<Notification> notifications = notificationRepository.findAllByUserOrderByCreatedAtDesc((user));
        return notifications.stream()
                .map(NotificationResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void markNotificationAsRead(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));
        if (!notification.getUser().getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.NOTIFICATION_ACCESS_DENIED);
        }
        notification.markAsRead();
    }
}
