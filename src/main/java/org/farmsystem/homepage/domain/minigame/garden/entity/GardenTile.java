package org.farmsystem.homepage.domain.minigame.garden.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

@Entity
@Table(name = "garden_tile")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GardenTile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tile_id", nullable = false)
    private Long tileId;

    @Column(name = "tile_type")  //잔디, 밭, 물, 돌 타일에 각각 0, 1, 2, 3 부여
    private Integer tileType;

    @Column(name = "x")
    private Long x;
    @Column(name = "y")
    private Long y;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

}
