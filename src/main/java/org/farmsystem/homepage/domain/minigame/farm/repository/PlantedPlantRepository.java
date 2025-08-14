package org.farmsystem.homepage.domain.minigame.farm.repository;

import org.farmsystem.homepage.domain.minigame.farm.entity.FarmplotTile;
import org.farmsystem.homepage.domain.minigame.farm.entity.PlantedPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlantedPlantRepository extends JpaRepository<PlantedPlant, Long> {
    Optional<PlantedPlant> findByFarmplotTile(FarmplotTile tile);
}
