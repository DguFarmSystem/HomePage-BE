package org.farmsystem.homepage.domain.user.repository;

import org.farmsystem.homepage.domain.user.entity.DailySeed;
import org.farmsystem.homepage.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailySeedRepository extends JpaRepository<DailySeed, Long> {
    Optional<DailySeed> findByUser(User user);
}
