package org.farmsystem.homepage.domain.minigame.player.dto.request;

import jakarta.validation.constraints.NotBlank;

public record GameTypeRequestDTO(
        @NotBlank String gameType
) {
}