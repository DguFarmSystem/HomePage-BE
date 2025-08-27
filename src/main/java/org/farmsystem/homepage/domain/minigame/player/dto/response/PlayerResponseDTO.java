package org.farmsystem.homepage.domain.minigame.player.dto.response;

import org.farmsystem.homepage.domain.minigame.player.entity.Player;

public record PlayerResponseDTO(
        int seedTicket,
        int gold,
        int sunlight,
        int seedCount,
        int level
) {
    public static PlayerResponseDTO from(Player p) {
        return new PlayerResponseDTO(
                p.getSeedTicket(),
                p.getGold(),
                p.getSunlight(),
                p.getSeedCount(),
                p.getLevel()
        );
    }
}
