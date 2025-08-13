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
    @Column(name = "store_id", nullable = false)  //???여기도 오토인크리먼트??
    private Long storeId;

    @Column(name = "object_name")
    private String objectplantName;

    @Column(name = "price")
    private Long price;
}
