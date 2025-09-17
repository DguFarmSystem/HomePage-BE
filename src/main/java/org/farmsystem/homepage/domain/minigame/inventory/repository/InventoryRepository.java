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
    Optional<Inventory> findFirstByPlayerAndGoodsType_GoodsNumberOrderByInventoryIdAsc(
            Player player, Long storeGoodsNumber);
    int countByPlayerAndGoodsType_GoodsNumber(Player player, Long storeGoodsNumber);

    @Query("""
            SELECT new org.farmsystem.homepage.domain.minigame.inventory.dto.response.InventoryResponseDTO(
                oi.goodsType.goodsNumber,
                COUNT(oi)
            )
            FROM Inventory oi
            WHERE oi.player = :player
            GROUP BY oi.goodsType.goodsNumber
            ORDER BY oi.goodsType.goodsNumber ASC
    """)
    List<InventoryResponseDTO> countInventoryGroupByGoodsType(@Param("player") Player p); //JPQL의 :player 자리에 p 객체를 맵핑
}
