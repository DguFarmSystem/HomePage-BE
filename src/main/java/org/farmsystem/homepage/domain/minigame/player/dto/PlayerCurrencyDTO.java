package org.farmsystem.homepage.domain.minigame.player.dto;

import jakarta.validation.constraints.NotNull;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

public record PlayerCurrencyDTO(
        @NotNull Integer seedTicket,
        @NotNull Integer gold,
        @NotNull Integer sunlight,
        @NotNull Integer seedCount
) {
    public static PlayerCurrencyDTO from(Player player) {
        return new PlayerCurrencyDTO(
                player.getSeedTicket(),
                player.getGold(),
                player.getSunlight(),
                player.getSeedCount()
        );
    }
}