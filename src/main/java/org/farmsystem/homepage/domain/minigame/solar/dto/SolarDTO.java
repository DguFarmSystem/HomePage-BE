package org.farmsystem.homepage.domain.minigame.solar.dto;

import org.farmsystem.homepage.domain.minigame.solar.entity.Solar;

import java.time.LocalDateTime;

public record SolarDTO( // TODO: Solar 무슨 DTO인지 (Response, Request) 써주기!
        LocalDateTime chargeStartTime
) {
    public static SolarDTO from(Solar station) {
        return new SolarDTO(
                station.getChargeStartedAt()
        );
    }
}
