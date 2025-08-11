package org.farmsystem.homepage.domain.minigame.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
}

