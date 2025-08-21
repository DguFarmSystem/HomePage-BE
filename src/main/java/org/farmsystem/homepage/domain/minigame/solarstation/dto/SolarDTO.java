package org.farmsystem.homepage.domain.minigame.solarstation.dto;

import org.farmsystem.homepage.domain.minigame.solarstation.entity.SolarPowerStation;

import java.time.LocalDateTime;

public record SolarDTO(
        LocalDateTime chargeStartTime,
        Integer level
) {
    public static SolarDTO from(SolarPowerStation station) {
        return new SolarDTO(
                station.getChargeStartedAt(),
                station.getLevel()
        );
    }
}
