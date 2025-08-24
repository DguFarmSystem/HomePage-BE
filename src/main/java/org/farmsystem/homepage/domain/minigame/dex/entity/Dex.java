package org.farmsystem.homepage.domain.minigame.dex.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Store;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

@Entity
@Table(name = "dex")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Dex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dex_id", nullable = false)
    private Long dexId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id")
    private Player player;

    // 식물 종류는 Store 마스터 테이블을 참조
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "owned_plant",
            referencedColumnName = "store_goods_number"
    )
    private Store ownedPlant;

    // 식물 등록 정적 팩토리 메서드
    public static Dex createDex(Player player, Store ownedPlant) {
        return new Dex(player, ownedPlant);
    }

    // private 생성자 → createDex 메서드 통해서만 생성
    private Dex(Player player, Store ownedPlant) {
        this.player = player;
        this.ownedPlant = ownedPlant;
    }
}
