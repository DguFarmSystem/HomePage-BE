package org.farmsystem.homepage.domain.minigame.solarstation.repository;

import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.solarstation.entity.SolarPowerStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SolarRepository extends JpaRepository<SolarPowerStation, Long> {
    Optional<SolarPowerStation> findByPlayer(Player player);
}
