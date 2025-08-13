package org.farmsystem.homepage.domain.minigame.farm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

@Entity
@Table(name = "dex")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dex_id", nullable = false)
    private Long dexId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(name = "owned_plant")
    private PlantType ownedPlant;
}
