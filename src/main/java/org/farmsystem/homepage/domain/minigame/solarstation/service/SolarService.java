package org.farmsystem.homepage.domain.minigame.solarstation.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.player.repository.PlayerRepository;
import org.farmsystem.homepage.domain.minigame.solarstation.dto.SolarDTO;
import org.farmsystem.homepage.domain.minigame.solarstation.entity.SolarPowerStation;
import org.farmsystem.homepage.domain.minigame.solarstation.repository.SolarRepository;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolarService {

    private final SolarRepository solarRepository;
    private final PlayerRepository playerRepository;

    @Transactional
    public SolarDTO getSolarStation(Long userId) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 없으면 생성
        SolarPowerStation station = solarRepository.findByPlayer(player)
                .orElseGet(() -> solarRepository.save(
                        SolarPowerStation.builder()
                                .player(player)
                                .level(0)
                                .chargeStartedAt(null)
                                .build()
                ));

        return SolarDTO.builder()
                .chargeStartTime(station.getChargeStartedAt())
                .level(station.getLevel())
                .build();
    }

    @Transactional
    public SolarDTO updateChargeTime(Long userId, SolarDTO request) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 없으면 생성
        SolarPowerStation station = solarRepository.findByPlayer(player)
                .orElseGet(() -> solarRepository.save(
                        SolarPowerStation.builder()
                                .player(player)
                                .level(0)
                                .chargeStartedAt(null)
                                .build()
                ));

        if (request.getChargeStartTime() != null) {
            station.setChargeStartedAt(request.getChargeStartTime());
        }
        if (request.getLevel() != null) {
            // 0~100 범위 제한 가능
            int newLevel = Math.max(0, Math.min(100, request.getLevel()));
            station.setLevel(newLevel);
        }

        return SolarDTO.builder()
                .chargeStartTime(station.getChargeStartedAt())
                .level(station.getLevel())
                .build();
    }
}
