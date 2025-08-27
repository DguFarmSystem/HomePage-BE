package org.farmsystem.homepage.domain.minigame.solarstation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.farmsystem.homepage.domain.minigame.solarstation.entity.SolarPowerStation;

import java.time.LocalDateTime;

public record SolarDTO(
        LocalDateTime chargeStartTime
) {
    public static SolarDTO from(SolarPowerStation station) {
        return new SolarDTO(
                station.getChargeStartedAt()
        );
    }
}
