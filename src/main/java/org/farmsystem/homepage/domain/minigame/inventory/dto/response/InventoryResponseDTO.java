package org.farmsystem.homepage.domain.minigame.inventory.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InventoryResponseDTO(
        @JsonProperty("object_type") Long objectType,
        @JsonProperty("object_count")Long objectCount
) {
}
