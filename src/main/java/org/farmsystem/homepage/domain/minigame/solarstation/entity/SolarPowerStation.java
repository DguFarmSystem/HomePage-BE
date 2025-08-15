package org.farmsystem.homepage.domain.minigame.solarstation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

import java.time.LocalDateTime;

@Entity
@Table(name = "solar_power_station")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SolarPowerStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id", nullable = false)
    private Long stationId;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "charge_started_at")
    private LocalDateTime chargeStartedAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false, unique = true)
    private Player player;

    public static SolarPowerStation createNew(Player player) {
        return SolarPowerStation.builder()
                .player(player)
                .level(0)
                .chargeStartedAt(null)
                .build();
    }

    public void startChargeAt(LocalDateTime startedAt) {
        this.chargeStartedAt = startedAt;
    }

    // 0~100 범위
    public void changeLevel(int newLevel) {
        int clamped = Math.max(0, Math.min(100, newLevel));
        this.level = clamped;
    }
}
