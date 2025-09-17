package org.farmsystem.homepage.domain.minigame.garden.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Store;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

@Entity
@Table(name = "garden_tile")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// TODO: public class Tile | @Table(name = "tile") 은 어때요
public class GardenTile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tile_id", nullable = false)
    private Long tileId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tile_type", nullable = false,
            referencedColumnName = "store_goods_number" // Store 테이블에서 참조할 컬럼명(DB 컬럼명)
    )
    private Store tileType;

    @Column(name = "x")
    private Long x;
    @Column(name = "y")
    private Long y;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    //정원 타일 생성 메소드
    public static GardenTile createGardenTile(Player player, long x, long y, Store tileType) {
        return GardenTile.builder()
                .player(player)
                .x(x)
                .y(y)
                .tileType(tileType)
                .build();
    }

    //정원 타일 업데이트 메소드
    public void updateTileType(Store newType) {
        this.tileType = newType;
    }

}
