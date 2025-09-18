package org.farmsystem.homepage.domain.minigame.inventory.repository;

import org.farmsystem.homepage.domain.minigame.inventory.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    Optional<Goods> findByGoodsNumber(Long storeGoodsNumber);  //오브젝트 or 식물 키값으로 조회

}
