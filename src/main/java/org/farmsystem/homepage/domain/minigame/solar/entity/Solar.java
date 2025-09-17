package org.farmsystem.homepage.domain.minigame.solar.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

import java.time.LocalDateTime;

@Entity
@Table(name = "solar")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
// TODO: public class Solar | @Table(name = "solar") 은 어때요
public class Solar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // TODO: 같은 맥락으로 solarId
    @Column(name = "solar_id", nullable = false)
    private Long solarId;

    @Column(name = "charge_started_at")
    private LocalDateTime chargeStartedAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false, unique = true)
    private Player player;

    // 플레이어의 태양광 발전소를 처음 생성
    public static Solar createSolarStation(Player player) {
        return new Solar(player, null);
    }

    // 충전 시작 시간 갱신
    public void updateChargeStartTime(LocalDateTime startedAt) {
        if (startedAt != null) {
            this.chargeStartedAt = startedAt;
        }
    }

    // createSolarStation으로만 생성
    private Solar(Player player, LocalDateTime chargeStartedAt) {
        this.player = player;
        this.chargeStartedAt = chargeStartedAt;
    }
}
