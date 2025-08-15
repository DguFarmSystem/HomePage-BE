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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyGameService {

    private final PlayerRepository playerRepository;
    private final DailyGameRepository dailyGameRepository;

    private Player findPlayerOrThrow(Long userId) {
        return playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));
    }

    private DailyGame getOrCreateDailyGame(Player player) {
        Optional<DailyGame> optional = dailyGameRepository.findByPlayer(player);
        return optional.orElseGet(() -> dailyGameRepository.save(DailyGame.createNew(player)));
    }

    @Transactional
    public GameCountResponse getGameCount(Long userId, String gameType) {
        Player player = findPlayerOrThrow(userId);
        DailyGame dailyGame = getOrCreateDailyGame(player);

        dailyGame.resetIfNeeded();
        return GameCountResponse.from(dailyGame, gameType);
    }

    @Transactional
    public GameCountResponse incrementGameCount(Long userId, GameTypeRequest request) {
        Player player = findPlayerOrThrow(userId);
        DailyGame dailyGame = getOrCreateDailyGame(player);

        dailyGame.resetIfNeeded();

        if (dailyGame.getGameCount(request.gameType()) >= 3) {
            throw new BusinessException(ErrorCode.DAILY_GAME_LIMIT_EXCEEDED);
        }

        dailyGame.incrementGame(request.gameType());
        return GameCountResponse.from(dailyGame, request.gameType());
    }
}
