package org.farmsystem.homepage.domain.minigame.dex.repository;

import org.farmsystem.homepage.domain.minigame.dex.entity.Dex;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Goods;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DexRepository extends JpaRepository<Dex, Long> {
    // 플레이어의 도감 전체 조회
    List<Dex> findByPlayer(Player player);

    // 이미 등록된 식물인지 여부 확인
    boolean existsByPlayerAndOwnedPlant(Player player, Goods ownedPlant);
}
