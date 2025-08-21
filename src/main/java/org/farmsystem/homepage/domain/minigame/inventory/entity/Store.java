package org.farmsystem.homepage.domain.minigame.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {  //enum 대신 오브젝트/식물 넘버와 이름으로 존재하는 전체 오브젝트/식물 관리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "store_goods_number",nullable = false, unique = true)
    private Long storeGoodsNumber;

    @Column(name = "store_goods_name")
    private String storeGoodsName;  //오브젝트 또는 식물 ..

    @Column(name = "price")
    private Long price;
}
