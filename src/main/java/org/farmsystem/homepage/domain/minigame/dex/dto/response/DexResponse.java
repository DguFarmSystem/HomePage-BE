package org.farmsystem.homepage.domain.minigame.dex.dto.response;

import org.farmsystem.homepage.domain.minigame.dex.entity.Dex;
import org.farmsystem.homepage.domain.minigame.dex.entity.PlantType;

public record DexResponse(
        Long dexId,
        PlantType ownedPlant
) {
    public static DexResponse from(Dex dex) {
        return new DexResponse(dex.getDexId(), dex.getOwnedPlant());
    }
}
