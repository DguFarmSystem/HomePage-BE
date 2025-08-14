package org.farmsystem.homepage.domain.minigame.player.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BadgeDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BadgeResponse {
        private Long badgeId;
        private Integer badgeType;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BadgeUpdateRequest {
        private Integer badgeType; // 추가할 칭호 종류
    }
}
