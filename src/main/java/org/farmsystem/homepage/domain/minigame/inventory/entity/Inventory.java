package org.farmsystem.homepage.domain.minigame.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

@Entity
@Table(name = "inventory")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory extends BaseTimeEntity { //획득한 오브젝트들을 관리하는 인벤토리

    // TODO: 각종 ID는 엔티티명_id로 일치시켜주세요! ex. inventoryId (inventory_id)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owned_id", nullable = false)
    private Long ownedId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    // 획득 오브젝트 종류(FK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "object_kind", nullable = false,
            referencedColumnName = "store_goods_number" // Store 테이블에서 참조할 컬럼명(DB 컬럼명)
    )
    private Store objectKind;
}
