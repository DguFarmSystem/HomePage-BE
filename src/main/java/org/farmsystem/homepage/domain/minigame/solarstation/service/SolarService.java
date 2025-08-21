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

    private Player findPlayerOrThrow(Long userId) {
        return playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private SolarPowerStation getOrCreateStation(Player player) {
        return solarRepository.findByPlayer(player)
                .orElseGet(() -> solarRepository.save(SolarPowerStation.createNew(player)));
    }

    @Transactional
    public SolarDTO getSolarStation(Long userId) {
        Player player = findPlayerOrThrow(userId);
        SolarPowerStation station = getOrCreateStation(player);
        return SolarDTO.from(station);
    }

    @Transactional
    public SolarDTO updateChargeTime(Long userId, SolarDTO request) {
        Player player = findPlayerOrThrow(userId);
        SolarPowerStation station = getOrCreateStation(player);

        if (request.chargeStartTime() != null) {
            station.startChargeAt(request.chargeStartTime());
        }
        if (request.level() != null) {
            station.changeLevel(request.level());
        }

        return SolarDTO.from(station);
    }
}
