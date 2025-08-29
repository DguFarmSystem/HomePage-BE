package org.farmsystem.homepage.domain.minigame.player.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.community.user.entity.User;

@Entity
@Table(name = "player")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id", nullable = false)
    private Long playerId;

    @Column(name = "seed_ticket", nullable = false)
    private int seedTicket = 0;

    @Column(nullable = false)
    private int gold = 0;

    @Column(nullable = false)
    private int sunlight = 0;

    @Column(nullable = false)
    private int seedCount = 0;

    @Column(nullable = false)
    private int level = 1;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public static Player createPlayer(User user) {
        return Player.builder()
                .user(user)
                .seedTicket(0)
                .gold(0)
                .sunlight(0)
                .seedCount(0)
                .level(1)
                .build();
    }

    public void updateCurrency(int seedTicket, int gold, int sunlight, int seedCount) {
        this.seedTicket = seedTicket;
        this.gold = gold;
        this.sunlight = sunlight;
        this.seedCount = seedCount;
    }

    public void updateLevel(int level) {
        this.level = level;
    }

    public void addTotalSeedTicket(int seedAmount) {
        this.seedTicket += seedAmount;
    }

}
