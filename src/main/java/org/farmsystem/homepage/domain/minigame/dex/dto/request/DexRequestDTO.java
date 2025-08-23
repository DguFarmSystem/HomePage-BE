package org.farmsystem.homepage.domain.minigame.dex.dto.request;

import jakarta.validation.constraints.NotNull;

public record DexRequestDTO(
        @NotNull Long ownedPlant  // Store의 store_goods_number를 의미
) {
}