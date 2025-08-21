package org.farmsystem.homepage.domain.minigame.garden.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.farmsystem.homepage.domain.minigame.garden.entity.PlacedObject;
import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;

public record PlaceObjectResponseDTO(
        Long x,
        Long y,
        @JsonProperty("object_type") Long objectType,
        Rotation rotation,
        int objectCount
) {
    public static PlaceObjectResponseDTO from(PlacedObject placedObject, int objectCount) {
        return new PlaceObjectResponseDTO(
                placedObject.getTile().getX(),
                placedObject.getTile().getY(),
                placedObject.getObjectKind().getStoreGoodsNumber(),
                placedObject.getRotation(),
                objectCount
        );
    }
}
