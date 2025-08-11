package org.farmsystem.homepage.domain.minigame.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.user.entity.User;

@Entity
@Table(name = "player")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {
    @Id
    @Column(name = "player_id", nullable = false)
    private Long playerId;

    @Column(nullable = false)
    private int gold = 0;

    @Column(nullable = false)
    private int sunlight = 0;

    @Column(name = "seed_ticket", nullable = false)
    private int seedTicket = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
