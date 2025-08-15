package org.farmsystem.homepage.domain.minigame.player.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.dto.request.GameTypeRequest;
import org.farmsystem.homepage.domain.minigame.player.dto.response.GameCountResponse;
import org.farmsystem.homepage.domain.minigame.player.entity.DailyGame;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.player.repository.DailyGameRepository;
import org.farmsystem.homepage.domain.minigame.player.repository.PlayerRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyGameService {

    private final PlayerRepository playerRepository;
    private final DailyGameRepository dailyGameRepository;

    @Transactional
    public GameCountResponse getGameCount(Long userId, String gameType) {
        // 없으면 생성
        DailyGame dailyGame = getOrCreateDailyGame(userId);

        resetIfNeeded(dailyGame);

        return new GameCountResponse(gameType, dailyGame.getGameCount(gameType));
    }

    @Transactional
    public GameCountResponse incrementGameCount(Long userId, GameTypeRequest request) {
        DailyGame dailyGame = getOrCreateDailyGame(userId);

        resetIfNeeded(dailyGame);

        int currentCount = dailyGame.getGameCount(request.gameType());
        if (currentCount >= 3) {
            throw new BusinessException(ErrorCode.DAILY_GAME_LIMIT_EXCEEDED);
        }

        dailyGame.incrementGame(request.gameType());

        return new GameCountResponse(request.gameType(), dailyGame.getGameCount(request.gameType()));
    }

    private DailyGame getOrCreateDailyGame(Long userId) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        return dailyGameRepository.findByPlayer(player)
                .orElseGet(() -> dailyGameRepository.save(
                        DailyGame.builder()
                                .player(player)
                                .rockScissors(0)
                                .carrotGame(0)
                                .sunlightGame(0)
                                .lastResetDate(LocalDate.now())
                                .build()
                ));
    }

    private void resetIfNeeded(DailyGame dailyGame) {
        if (dailyGame.getLastResetDate() == null || !dailyGame.getLastResetDate().equals(LocalDate.now())) {
            dailyGame.resetCounts();
        }
    }
}
