package org.farmsystem.homepage.domain.minigame.farm.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

public class FarmDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TileResponse {
        private int x;
        private int y;
        private String status;
        private LocalDateTime plantedAt;
        private int sunlightCount;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TileUpdateRequest {
        private int x;
        private int y;
        private String status; // empty, planted, ready
        private LocalDateTime plantedAt;
        private int sunlightCount;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FarmResponse {
        private List<TileResponse> tiles;
    }
}
