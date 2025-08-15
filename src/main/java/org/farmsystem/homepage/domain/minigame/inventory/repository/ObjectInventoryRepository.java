package org.farmsystem.homepage.domain.minigame.inventory.repository;

import org.farmsystem.homepage.domain.minigame.inventory.entity.ObjectInventory;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObjectInventoryRepository extends JpaRepository<ObjectInventory, Long> {
    List<ObjectInventory> findByPlayerAndObjectKind_StoreGoodsNumber(Player player, Long storeGoodsNumber);
    int countByPlayerAndObjectKind_StoreGoodsNumber(Player player, Long storeGoodsNumber);
}
