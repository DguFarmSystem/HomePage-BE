package org.farmsystem.homepage.domain.community.user.dto.response;

import org.farmsystem.homepage.domain.community.user.entity.DailySeed;

public record TodaySeedResponseDTO(
        boolean isAttendance,
        boolean isCheer,
        boolean isFarminglog
) {
    public static TodaySeedResponseDTO from(DailySeed dailySeed) {
        return new TodaySeedResponseDTO(
                dailySeed.isAttendance(),
                dailySeed.isCheer(),
                dailySeed.isFarminglog()
        );
    }
}
