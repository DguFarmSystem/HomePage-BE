package org.farmsystem.homepage.domain.minigame.player.dto.response;

import org.farmsystem.homepage.domain.minigame.player.entity.DailyGame;

public record GameCountResponseDTO(
        String gameType,
        int count
) {
    public static GameCountResponseDTO from(DailyGame dailyGame, String gameType) {
        return new GameCountResponseDTO(gameType, dailyGame.getGameCount(gameType));
    }
}
