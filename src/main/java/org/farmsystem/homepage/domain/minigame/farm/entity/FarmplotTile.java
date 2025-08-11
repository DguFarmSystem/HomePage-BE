package org.farmsystem.homepage.domain.minigame.farm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

@Entity
@Table(name = "farmplot_tile")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmplotTile {
    @Id
    @Column(name = "tile_num", nullable = false) //1~9까지 타일 번호 부여
    private Integer tileNum;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
}

