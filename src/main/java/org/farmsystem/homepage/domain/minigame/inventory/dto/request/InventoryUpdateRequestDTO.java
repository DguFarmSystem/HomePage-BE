package org.farmsystem.homepage.domain.minigame.inventory.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InventoryUpdateRequestDTO(
        @JsonProperty("object_type") Long objectType,
        @JsonProperty("object_count") int objectCount
) {
}
