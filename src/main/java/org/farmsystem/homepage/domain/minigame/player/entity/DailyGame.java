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

    @Column(name = "rock_scissors_game", nullable = false)
    private Integer rockScissors; // 남은 횟수

    @Column(name = "carrot_game", nullable = false)
    private Integer carrotGame; // 남은 횟수

    @Column(name = "sunlight_game", nullable = false)
    private Integer sunlightGame; // 남은 횟수

    @Column(name = "last_reset_date", nullable = false)
    private LocalDate lastResetDate;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    public static DailyGame createDailyGame(Player player) {
        return new DailyGame(player, 3, 3, 3, LocalDate.now());
    }

    // 날짜가 바뀌었으면 횟수를 초기화한다 (다시 3회로 초기화)
    public void resetIfNeeded() {
        if (!LocalDate.now().equals(this.lastResetDate)) {
            resetCounts();
        }
    }
    public void resetCounts() {
        this.rockScissors = 3;
        this.carrotGame = 3;
        this.sunlightGame = 3;
        this.lastResetDate = LocalDate.now();
    }

    // 게임 횟수를 1회 차감(0보다 작아질 수 없음)
    public void useGame(String gameType) {
        switch (gameType.toLowerCase()) {
            case "rock" -> {
                if (rockScissors <= 0) throw new IllegalStateException("rock game 횟수 모두 소진됨");
                this.rockScissors--;
            }
            case "carrot" -> {
                if (carrotGame <= 0) throw new IllegalStateException("carrot game 횟수 모두 소진됨");
                this.carrotGame--;
            }
            case "sunlight" -> {
                if (sunlightGame <= 0) throw new IllegalStateException("sunlight game 횟수 모두 소진됨");
                this.sunlightGame--;
            }
            default -> throw new IllegalArgumentException("Invalid game type: " + gameType);
        }
    }
    // 현재 게임의 남은 횟수 반환
    public int getRemainingCount(String gameType) {
        return switch (gameType.toLowerCase()) {
            case "rock" -> this.rockScissors;
            case "carrot" -> this.carrotGame;
            case "sunlight" -> this.sunlightGame;
            default -> throw new IllegalArgumentException("Invalid game type: " + gameType);
        };
    }

    private DailyGame(Player player, int rock, int carrot, int sunlight, LocalDate resetDate) {
        this.player = player;
        this.rockScissors = rock;
        this.carrotGame = carrot;
        this.sunlightGame = sunlight;
        this.lastResetDate = resetDate;
    }
}
