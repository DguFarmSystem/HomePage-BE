package org.farmsystem.homepage.domain.minigame.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

@Entity
@Table(name = "object_inventory")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjectInventory { //획득한 오브젝트들을 관리하는 인벤토리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owned_id", nullable = false)
    private Long owned_id;

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
