package org.farmsystem.homepage.domain.community.notification.repository;

import org.farmsystem.homepage.domain.community.notification.entity.Notification;
import org.farmsystem.homepage.domain.community.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserOrderByCreatedAtDesc(User user);
}
