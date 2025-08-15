package org.farmsystem.homepage.domain.minigame.inventory.repository;

import org.farmsystem.homepage.domain.minigame.inventory.dto.response.InventoryResponseDTO;
import org.farmsystem.homepage.domain.minigame.inventory.entity.ObjectInventory;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ObjectInventoryRepository extends JpaRepository<ObjectInventory, Long> {
    List<ObjectInventory> findByPlayerAndObjectKind_StoreGoodsNumber(Player player, Long storeGoodsNumber);
    Optional<ObjectInventory> findFirstByPlayerAndObjectKind_StoreGoodsNumberOrderByOwnedIdAsc(
            Player player, Long storeGoodsNumber);
    int countByPlayerAndObjectKind_StoreGoodsNumber(Player player, Long storeGoodsNumber);

    @Query("""
            SELECT new org.farmsystem.homepage.domain.minigame.inventory.dto.response.InventoryResponseDTO(
                oi.objectKind.storeGoodsNumber,
                COUNT(oi)
            )
            FROM ObjectInventory oi
            WHERE oi.player = :player
            GROUP BY oi.objectKind.storeGoodsNumber
    """)
    List<InventoryResponseDTO> countInventoryGroupByObjectKind(@Param("player") Player p); //JPQL의 :player 자리에 p 객체를 맵핑
}
