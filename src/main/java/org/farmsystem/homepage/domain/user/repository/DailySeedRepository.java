package org.farmsystem.homepage.domain.user.repository;

import jakarta.persistence.LockModeType;
import org.farmsystem.homepage.domain.user.entity.DailySeed;
import org.farmsystem.homepage.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface DailySeedRepository extends JpaRepository<DailySeed, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<DailySeed> findByUser(User user);
}
