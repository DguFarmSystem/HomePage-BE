package org.farmsystem.homepage.domain.minigame.dex.dto.response;

import org.farmsystem.homepage.domain.minigame.dex.entity.PlantType;

public record DexResponse(
        Long dexId,
        PlantType ownedPlant
) {
}