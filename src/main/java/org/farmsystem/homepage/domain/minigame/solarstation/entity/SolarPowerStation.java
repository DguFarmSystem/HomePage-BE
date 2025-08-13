package org.farmsystem.homepage.domain.minigame.solarstation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

import java.time.LocalDateTime;

@Setter
@Entity
@Table(name = "solar_power_station")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolarPowerStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id", nullable = false)
    private Long stationId;

    @Column(name = "level", nullable = false)
    private int level = 0;

    @Column(name = "charge_started_at")
    private LocalDateTime chargeStartedAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false, unique = true)
    private Player player;
}

