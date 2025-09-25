package org.farmsystem.homepage.domain.minigame.farm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

@Entity
@Table(name = "farmplot_tile")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmplotTile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farmplot_tile_id", nullable = false)
    private Long farmPlotTileId; // PK

    @Column(name = "tile_num", nullable = false) // 1~9 타일 번호
    private Integer tileNum;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id")
    private Player player;
}
