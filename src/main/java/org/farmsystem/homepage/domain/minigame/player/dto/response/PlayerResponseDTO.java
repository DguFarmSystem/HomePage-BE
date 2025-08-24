package org.farmsystem.homepage.domain.minigame.player.dto.response;

import org.farmsystem.homepage.domain.minigame.player.entity.Player;

public record PlayerResponseDTO(
        Long playerId,
        Long userId,
        int seedTicket,
        int gold,
        int sunlight,
        int level
) {
    public static PlayerResponseDTO from(Player p) {
        return new PlayerResponseDTO(
                p.getPlayerId(),
                p.getUser().getUserId(),
                p.getSeedTicket(),
                p.getGold(),
                p.getSunlight(),
                p.getLevel()
        );
    }
}
