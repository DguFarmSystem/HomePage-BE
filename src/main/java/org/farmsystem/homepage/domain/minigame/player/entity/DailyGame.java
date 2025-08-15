package org.farmsystem.homepage.domain.minigame.player.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_game")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "rock_scissors_game")
    private Integer rockScissors;

    @Column(name = "carrot_game")
    private Integer carrotGame;

    @Column(name = "sunlight_game")
    private Integer sunlightGame;

    @Column(name = "last_reset_date")
    private LocalDate lastResetDate;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    public void resetCounts() {
        this.rockScissors = 0;
        this.carrotGame = 0;
        this.sunlightGame = 0;
        this.lastResetDate = LocalDate.now();
    }

    public void incrementGame(String gameType) {
        switch (gameType.toLowerCase()) {
            case "rock" -> this.rockScissors++;
            case "carrot" -> this.carrotGame++;
            case "sunlight" -> this.sunlightGame++;
            default -> throw new IllegalArgumentException("Invalid game type: " + gameType);
        }
    }

    public int getGameCount(String gameType) {
        return switch (gameType.toLowerCase()) {
            case "rock" -> this.rockScissors;
            case "carrot" -> this.carrotGame;
            case "sunlight" -> this.sunlightGame;
            default -> throw new IllegalArgumentException("Invalid game type: " + gameType);
        };
    }
}
