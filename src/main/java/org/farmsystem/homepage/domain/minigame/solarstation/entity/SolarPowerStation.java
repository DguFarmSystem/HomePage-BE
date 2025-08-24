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

    // 플레이어의 태양광 발전소를 처음 생성
    public static SolarPowerStation createSolarStation(Player player) {
        return new SolarPowerStation(player, 0, null);
    }

    // 발전소 상태 업데이트
    public void updateState(LocalDateTime startedAt, Integer newLevel) {
        if (startedAt != null) {
            this.chargeStartedAt = startedAt;
        }
        if (newLevel != null) {
            int clamped = Math.max(0, Math.min(100, newLevel));
            this.level = clamped;
        }
    }

    // createSolarStation으로만 생성
    private SolarPowerStation(Player player, int level, LocalDateTime chargeStartedAt) {
        this.player = player;
        this.level = level;
        this.chargeStartedAt = chargeStartedAt;
    }
}
