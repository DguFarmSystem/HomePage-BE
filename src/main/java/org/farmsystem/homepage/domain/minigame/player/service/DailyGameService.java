package org.farmsystem.homepage.domain.minigame.player.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.dto.request.GameTypeRequestDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.response.GameCountResponseDTO;
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
        return dailyGameRepository.findByPlayer(player)
                .orElseGet(() -> dailyGameRepository.save(DailyGame.createDailyGame(player)));
    }

    // 게임의 남은 횟수 조회
    @Transactional
    public GameCountResponseDTO getGameCount(Long userId, String gameType) {
        Player player = findPlayerOrThrow(userId);
        DailyGame dailyGame = getOrCreateDailyGame(player);

        dailyGame.resetIfNeeded();
        return GameCountResponseDTO.from(dailyGame, gameType);
    }

    // 게임 횟수 1회 소모
    @Transactional
    public GameCountResponseDTO useGame(Long userId, GameTypeRequestDTO request) {
        Player player = findPlayerOrThrow(userId);
        DailyGame dailyGame = getOrCreateDailyGame(player);

        dailyGame.resetIfNeeded();

        if (dailyGame.getRemainingCount(request.gameType()) <= 0) {
            throw new BusinessException(ErrorCode.DAILY_GAME_LIMIT_EXCEEDED);
        }

        dailyGame.useGame(request.gameType());
        return GameCountResponseDTO.from(dailyGame, request.gameType());
    }
}
