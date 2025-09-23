package org.farmsystem.homepage.domain.minigame.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "goods")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Goods {  //enum 대신 오브젝트/식물 넘버와 이름으로 존재하는 전체 오브젝트/식물 관리

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id", nullable = false)
    private Long goodsId;

    @Column(name = "goods_number",nullable = false, unique = true)
    private Long goodsNumber;

    @Column(name = "goods_name")
    private String goodsName;  //오브젝트 또는 식물 또는 타일

    @Column(name = "goods_name_kr")
    private String goodsNameKr;

    @Column(name = "purchase_price")
    private Long purchasePrice;

    @Column(name = "sale_price")
    private Long salePrice;

}
