package org.farmsystem.homepage.domain.minigame.solar.repository;

import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.solar.entity.Solar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 플레이어별 발전소 조회
public interface SolarRepository extends JpaRepository<Solar, Long> {
    Optional<Solar> findByPlayer(Player player);
}
