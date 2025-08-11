package org.farmsystem.homepage.domain.minigame.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

