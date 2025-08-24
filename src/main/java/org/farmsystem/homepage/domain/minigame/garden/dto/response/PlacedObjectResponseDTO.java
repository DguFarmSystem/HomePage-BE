package org.farmsystem.homepage.domain.minigame.garden.dto.response;

import org.farmsystem.homepage.domain.minigame.garden.entity.PlacedObject;
import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;

public record PlacedObjectResponseDTO(
        Long objectKind,
        Rotation rotation

) {
    public static PlacedObjectResponseDTO from(PlacedObject object) {
        if (object == null) return null;
        return new PlacedObjectResponseDTO(
                object.getObjectKind().getStoreGoodsNumber(),
                object.getRotation()
        );
    }
}
