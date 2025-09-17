package org.farmsystem.homepage.domain.minigame.garden.dto.response;

import org.farmsystem.homepage.domain.minigame.garden.entity.PlacedObject;
import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;

public record PlacedObjectResponseDTO(
        Long objectKind, // TODO: Long인 이유?!
        Rotation rotation
) {
    public static PlacedObjectResponseDTO from(PlacedObject object) {
        if (object == null) return null;
        return new PlacedObjectResponseDTO(
                object.getObjectType().getGoodsNumber(),
                object.getRotation()
        );
    }
}
