package org.farmsystem.homepage.domain.community.notification.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.community.user.entity.User;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@RequiredArgsConstructor
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationType type;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 50)
    private String message;

    @Column(nullable = false, length = 100)
    private String targetUrl;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean isRead;

    @Builder
    public Notification(User user, NotificationType type, String title, String message, String targetUrl, boolean isRead) {
        this.user = user;
        this.type = type;
        this.title = title;
        this.message = message;
        this.targetUrl = targetUrl;
        this.isRead = isRead;
    }

    public void markAsRead() {
        if (!this.isRead) {
            this.isRead = true;
        }
    }
}
