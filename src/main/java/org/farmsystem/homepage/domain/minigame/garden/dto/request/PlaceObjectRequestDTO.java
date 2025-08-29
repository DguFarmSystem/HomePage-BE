package org.farmsystem.homepage.domain.minigame.garden.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.farmsystem.homepage.domain.minigame.garden.entity.Rotation;

public record PlaceObjectRequestDTO(
        Long x,
        Long y,

        //TODO : PlaceObjectRequestDTO랑 ChangePlacedObjectRequestDTO 변수명 받을 때 스네이크 케이스(object_type) 쓴 이유가 있나요?
        // 아니면 다른 DTO들이랑 통일해주면 좋을 듯! (카멜 or 스네이크)
        @JsonProperty("object_type") Long objectType,
        Rotation rotation
) {

}
