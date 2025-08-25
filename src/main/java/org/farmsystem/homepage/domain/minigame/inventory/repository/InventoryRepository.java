package org.farmsystem.homepage.domain.minigame.inventory.repository;

import org.farmsystem.homepage.domain.minigame.inventory.dto.response.InventoryResponseDTO;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Inventory;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByPlayerAndObjectKind_StoreGoodsNumber(Player player, Long storeGoodsNumber);
    Optional<Inventory> findFirstByPlayerAndObjectKind_StoreGoodsNumberOrderByOwnedIdAsc(
            Player player, Long storeGoodsNumber);
    int countByPlayerAndObjectKind_StoreGoodsNumber(Player player, Long storeGoodsNumber);

    @Query("""
            SELECT new org.farmsystem.homepage.domain.minigame.inventory.dto.response.InventoryResponseDTO(
                oi.objectKind.storeGoodsNumber,
                COUNT(oi)
            )
            FROM Inventory oi
            WHERE oi.player = :player
            GROUP BY oi.objectKind.storeGoodsNumber
            ORDER BY oi.objectKind.storeGoodsNumber ASC
    """)
    List<InventoryResponseDTO> countInventoryGroupByObjectKind(@Param("player") Player p); //JPQL의 :player 자리에 p 객체를 맵핑
}
