package org.farmsystem.homepage.domain.minigame.solarstation.dto;

import org.farmsystem.homepage.domain.minigame.solarstation.entity.SolarPowerStation;

import java.time.LocalDateTime;

public record SolarDTO( // TODO: Solar 무슨 DTO인지 (Response, Request) 써주기!
        LocalDateTime chargeStartTime
) {
    public static SolarDTO from(SolarPowerStation station) {
        return new SolarDTO(
                station.getChargeStartedAt()
        );
    }
}
