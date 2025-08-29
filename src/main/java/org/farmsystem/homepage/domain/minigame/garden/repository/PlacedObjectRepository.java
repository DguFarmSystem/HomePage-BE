package org.farmsystem.homepage.domain.minigame.garden.repository;

import org.farmsystem.homepage.domain.minigame.garden.entity.GardenTile;
import org.farmsystem.homepage.domain.minigame.garden.entity.PlacedObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlacedObjectRepository extends JpaRepository<PlacedObject, Long> {
    Optional<PlacedObject> findByTile(GardenTile tile);
    void deleteByTile(GardenTile tile);

}
