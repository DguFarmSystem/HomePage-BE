package org.farmsystem.homepage.domain.minigame.solarstation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolarDTO {
    private LocalDateTime chargeStartTime;
    private Integer level; // null이면 수정 안 함
}
