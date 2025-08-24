package org.farmsystem.homepage.domain.minigame.player.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.user.entity.User;

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
    private int level = 1;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void update(int seedTicket, int gold, int sunlight) {
        this.seedTicket = seedTicket;
        this.gold = gold;
        this.sunlight = sunlight;
    }

    public void addTotalSeedTicket(int seedAmount) {
        this.seedTicket += seedAmount;
    }


}
