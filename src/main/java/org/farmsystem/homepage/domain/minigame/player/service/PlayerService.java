package org.farmsystem.homepage.domain.minigame.player.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.farmingLog.dto.FarmingLogResponseDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.PlayerCurrencyDTO;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.player.repository.PlayerRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlayerService {

    private final PlayerRepository playerRepository;

    //플레이어 재화 업데이트
    public PlayerCurrencyDTO updatePlayer(Long userId, PlayerCurrencyDTO requestDTO) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        player.update(requestDTO.seedticket(), requestDTO.gold(), requestDTO.sunlight());
        return PlayerCurrencyDTO.from(player);

    }

    //플레이어 재화 조회
    @Transactional(readOnly = true)
    public PlayerCurrencyDTO getPlayerCurrency(Long userId) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        return PlayerCurrencyDTO.from(player);
    }



}
