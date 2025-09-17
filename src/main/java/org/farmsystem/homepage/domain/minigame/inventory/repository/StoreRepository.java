package org.farmsystem.homepage.domain.minigame.inventory.repository;

import org.farmsystem.homepage.domain.minigame.inventory.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByStoreGoodsNumber(Long storeGoodsNumber);  //오브젝트 or 식물 키값으로 조회
    Optional<Store> findByStoreGoodsName(String storeGoodsName);  //오브젝트 or 식물 이름으로 조회
}
