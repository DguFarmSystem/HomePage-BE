package org.farmsystem.homepage.domain.minigame.farm.repository;

import org.farmsystem.homepage.domain.minigame.farm.entity.FarmplotTile;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FarmplotTileRepository extends JpaRepository<FarmplotTile, Long> {
    List<FarmplotTile> findByPlayer(Player player);
    Optional<FarmplotTile> findByPlayerAndTileNum(Player player, int tileNum);
}
