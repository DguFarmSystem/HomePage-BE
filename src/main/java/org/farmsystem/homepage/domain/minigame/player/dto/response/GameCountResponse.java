package org.farmsystem.homepage.domain.minigame.player.dto.response;

import org.farmsystem.homepage.domain.minigame.player.entity.DailyGame;

public record GameCountResponse(
        String gameType,
        int count
) {
    public static GameCountResponse from(DailyGame dailyGame, String gameType) {
        return new GameCountResponse(gameType, dailyGame.getGameCount(gameType));
    }
}
