package org.farmsystem.homepage.domain.minigame.entity;

import jakarta.persistence.*;
import lombok.*;

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

    // 스키마상 uid. 기존 맥락상 Player로 해석
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(name = "owned_plant")
    private PlantType ownedPlant;
}
