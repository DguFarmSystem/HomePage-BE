package org.farmsystem.homepage.domain.minigame.player.repository;

import org.farmsystem.homepage.domain.minigame.player.entity.Badge;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findByPlayer(Player player);
}
