package org.farmsystem.homepage.domain.minigame.inventory.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.farmsystem.homepage.domain.minigame.dex.entity.PlantType;

public record InventoryPlantUpdateRequestDTO(
        @JsonProperty("plant_type")PlantType plantType,
        @JsonProperty("plant_count") int plantCount
){
}
