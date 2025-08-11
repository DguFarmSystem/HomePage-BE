package org.farmsystem.homepage.domain.minigame.farm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

import java.time.LocalDateTime;

@Entity
@Table(name = "planted_plant")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantedPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id", nullable = false)
    private Long plantId;

    @Column(name = "planted_at")
    private LocalDateTime plantedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PlantStatus status;

    @Column(name = "sunlight_count")
    private Integer sunlightCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tile_num", nullable = false)
    private FarmplotTile farmplotTile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
}
