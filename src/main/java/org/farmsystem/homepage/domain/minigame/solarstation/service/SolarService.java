package org.farmsystem.homepage.domain.minigame.solarstation.service;


import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.player.repository.PlayerRepository;
import org.farmsystem.homepage.domain.minigame.solarstation.dto.SolarDTO;
import org.farmsystem.homepage.domain.minigame.solarstation.entity.SolarPowerStation;
import org.farmsystem.homepage.domain.minigame.solarstation.repository.SolarRepository;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SolarService {

    private final SolarRepository solarRepository;
    private final PlayerRepository playerRepository;
    // 플레이어 조회
    private Player findPlayerOrThrow(Long userId) {
        return playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
    // 플레이어에게 발전소가 없으면 생성
    private SolarPowerStation getOrCreateStation(Player player) {
        return solarRepository.findByPlayer(player)
                .orElseGet(() -> solarRepository.save(SolarPowerStation.createSolarStation(player)));
    }
    // 플레이어의 태양광 발전소 조회
    @Transactional
    public SolarDTO getSolarStation(Long userId) {
        Player player = findPlayerOrThrow(userId);
        SolarPowerStation station = getOrCreateStation(player);
        return SolarDTO.from(station);
    }
    // 발전소 상태(충전 시간, 레벨) 업데이트
    @Transactional
    public SolarDTO updateChargeTime(Long userId, SolarDTO request) {
        Player player = findPlayerOrThrow(userId);
        SolarPowerStation station = getOrCreateStation(player);

        station.updateChargeStartTime(request.chargeStartTime());

        return SolarDTO.from(station);
    }
}
