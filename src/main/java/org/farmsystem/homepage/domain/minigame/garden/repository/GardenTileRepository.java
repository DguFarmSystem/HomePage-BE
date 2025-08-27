package org.farmsystem.homepage.domain.minigame.garden.repository;

import org.farmsystem.homepage.domain.minigame.garden.entity.GardenTile;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GardenTileRepository extends JpaRepository<GardenTile, Long> {
    List<GardenTile> findByPlayer(Player player);
    List<GardenTile> findByPlayerOrderByXAscYAsc(Player player);
    boolean existsByPlayerAndXAndY(Player player, Long x, Long y);
    Optional<GardenTile> findByPlayerAndXAndY(Player player, Long x, Long y);
}
