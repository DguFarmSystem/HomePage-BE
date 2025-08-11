package org.farmsystem.homepage.domain.minigame.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "daily_game")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyGame {
    @Id
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "rock_scissors_game")
    private Integer rockScissors;

    @Column(name = "carrot_game")
    private Integer carrotGame;

    @Column(name = "sunlight_game")
    private Integer sunlightGame;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
}
