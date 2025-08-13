package org.farmsystem.homepage.domain.minigame.player.dto;

import org.farmsystem.homepage.domain.minigame.player.entity.Player;

public record PlayerCurrencyDTO(
        int seedticket,
        int gold,
        int sunlight
) {
    public static PlayerCurrencyDTO from(Player player) {
        return new PlayerCurrencyDTO(
                player.getSeedTicket(),
                player.getGold(),
                player.getSunlight()
        );
    }
}
