package org.farmsystem.homepage.domain.minigame.dex.dto.request;

import org.farmsystem.homepage.domain.minigame.dex.entity.PlantType;

public record DexRequest(
        PlantType ownedPlant
) {
}