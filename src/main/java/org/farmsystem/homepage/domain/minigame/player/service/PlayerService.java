package org.farmsystem.homepage.domain.minigame.player.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.dto.PlayerCurrencyDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.request.LevelUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.response.PlayerResponseDTO;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.player.repository.PlayerRepository;
import org.farmsystem.homepage.domain.community.user.entity.User;
import org.farmsystem.homepage.domain.community.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    //플레이어 재화 업데이트
    public PlayerCurrencyDTO updateCurrency(Long userId, PlayerCurrencyDTO requestDTO) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        player.updateCurrency(requestDTO.seedTicket(), requestDTO.gold(), requestDTO.sunlight(), requestDTO.seedCount());
        return PlayerCurrencyDTO.from(player);

    }

    //플레이어 레벨 업데이트
    public PlayerResponseDTO updateLevel(Long userId, LevelUpdateRequestDTO requestDTO) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        player.updateLevel(requestDTO.newLevel());
        return PlayerResponseDTO.from(player);
    }

    //플레이어 재화 조회
    @Transactional(readOnly = true)
    public PlayerResponseDTO getPlayerCurrency(Long userId) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        return PlayerResponseDTO.from(player);
    }

    @Transactional
    public PlayerResponseDTO createPlayer(Long userId) {
        //이미 있으면 그대로 반환
        Optional<Player> existing = playerRepository.findByUser_UserId(userId);
        if (existing.isPresent()) {
            return PlayerResponseDTO.from(existing.get());
        }

        User user = userRepository.findById(userId) .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        try {
            Player created = playerRepository.save(Player.createPlayer(user));
            return PlayerResponseDTO.from(created);
        }
        catch (DataIntegrityViolationException e) { //user UNIQUE 제약 위반 시
            //거의 동시에 다른 요청이 같은 userId로 생성 완료한 경우 대비
            Player first = playerRepository.findByUser_UserId(userId)
                    .orElseThrow(() -> e);
            return PlayerResponseDTO.from(first);
        }
    }

}