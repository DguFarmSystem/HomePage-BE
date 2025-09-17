package org.farmsystem.homepage.domain.minigame.garden.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.farmsystem.homepage.domain.minigame.garden.entity.PlacedObject;
import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;

public record ChangePlacedObjectResponseDTO(
        Long x, // TODO: Long인 이유 있을까요?
        Long y,
        @JsonProperty("object_type") Long objectType,
        Rotation rotation
) {
    public static ChangePlacedObjectResponseDTO from(PlacedObject placedObject) {
        return new ChangePlacedObjectResponseDTO(
                placedObject.getTile().getX(),
                placedObject.getTile().getY(),
                placedObject.getObjectKind().getStoreGoodsNumber(),
                placedObject.getRotation()
        );
    }
}
