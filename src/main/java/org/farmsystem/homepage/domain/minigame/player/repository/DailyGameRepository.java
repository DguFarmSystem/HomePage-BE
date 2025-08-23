package org.farmsystem.homepage.domain.minigame.player.repository;

import org.farmsystem.homepage.domain.minigame.player.entity.DailyGame;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyGameRepository extends JpaRepository<DailyGame, Long> {
    Optional<DailyGame> findByPlayer(Player player);
}
