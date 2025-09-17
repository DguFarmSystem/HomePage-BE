package org.farmsystem.homepage.domain.minigame.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// TODO: Store 대신 Goods 같은 이름으로 바꾸는 것을 추천
//  이유: GardenTile에서 'private Store tileType;' 이 있는데, 이해하기 어렵다.
// 필드명에서도 store 빼서 한꺼번에 슉슉 정리하고
public class Store {  //enum 대신 오브젝트/식물 넘버와 이름으로 존재하는 전체 오브젝트/식물 관리

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "store_goods_number",nullable = false, unique = true)
    private Long storeGoodsNumber;

    @Column(name = "store_goods_name")
    private String storeGoodsName;  //오브젝트 또는 식물 ..

    @Column(name = "store_goods_name_kr")
    private String storeGoodsNameKr;

    @Column(name = "purchasePrice")
    private Long purchasePrice;

    @Column(name = "salePrice")
    private Long salePrice;

}
