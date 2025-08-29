package org.farmsystem.homepage.domain.minigame.inventory.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InventoryResponseDTO(
        // TODO: object_type 처럼 바꾸면 안 되는 format이 있는 경우에는 꼭 주석 달아주세요 ~ 전체적으로 보면서 바꾸는 안 되는 네이밍은 주석 달아야 할듯!!
        @JsonProperty("object_type") Long objectType,
        @JsonProperty("object_count")Long objectCount
) {
}
