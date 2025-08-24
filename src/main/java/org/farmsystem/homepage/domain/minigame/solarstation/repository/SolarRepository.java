package org.farmsystem.homepage.domain.minigame.solarstation.repository;

import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.solarstation.entity.SolarPowerStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 플레이어별 발전소 조회
public interface SolarRepository extends JpaRepository<SolarPowerStation, Long> {
    Optional<SolarPowerStation> findByPlayer(Player player);
}
