package org.farmsystem.homepage.domain.minigame.dex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.farmsystem.homepage.domain.minigame.dex.entity.PlantType;

import java.util.List;

public class DexDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DexRequest {
        private PlantType ownedPlant;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DexResponse {
        private Long dexId;
        private PlantType ownedPlant;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DexListResponse {
        private List<DexResponse> dexList;
    }
}
