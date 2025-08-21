package org.farmsystem.homepage.domain.minigame.dex.repository;

import org.farmsystem.homepage.domain.minigame.dex.entity.Dex;
import org.farmsystem.homepage.domain.minigame.dex.entity.PlantType;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DexRepository extends JpaRepository<Dex, Long> {
    List<Dex> findByPlayer(Player player);

    boolean existsByPlayerAndOwnedPlant(Player player, PlantType ownedPlant);
}
