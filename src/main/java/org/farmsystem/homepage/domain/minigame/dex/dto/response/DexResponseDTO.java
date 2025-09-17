package org.farmsystem.homepage.domain.minigame.dex.dto.response;

import org.farmsystem.homepage.domain.minigame.dex.entity.Dex;

public record DexResponseDTO(
        Long dexId,
        Long ownedPlant // Store의 store_goods_number를 의미
) {
    public static DexResponseDTO from(Dex dex) {
        return new DexResponseDTO(
                dex.getDexId(),
                dex.getOwnedPlant().getGoodsNumber()
        );
    }
}
