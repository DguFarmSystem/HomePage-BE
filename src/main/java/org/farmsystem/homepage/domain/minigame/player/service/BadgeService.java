package org.farmsystem.homepage.domain.minigame.player.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.dto.request.BadgeUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.response.BadgeResponseDTO;
import org.farmsystem.homepage.domain.minigame.player.entity.Badge;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.player.repository.BadgeRepository;
import org.farmsystem.homepage.domain.minigame.player.repository.PlayerRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final PlayerRepository playerRepository;

    // 플레이어 조회
    private Player findPlayerOrThrow(Long userId) {
        return playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));
    }
    // 플레이어의 전체 칭호 조회
    @Transactional(readOnly = true)
    public List<BadgeResponseDTO> getBadges(Long userId) {
        Player player = findPlayerOrThrow(userId);

        return badgeRepository.findByPlayer(player)
                .stream()
                .map(BadgeResponseDTO::from)
                .toList();
    }
    // 새로운 칭호 등록
    @Transactional
    public BadgeResponseDTO addBadge(Long userId, BadgeUpdateRequestDTO request) {
        Player player = findPlayerOrThrow(userId);

        if (badgeRepository.existsByPlayerAndBadgeType(player, request.badgeType())) {
            throw new BusinessException(ErrorCode.BADGE_ALREADY_EXISTS);
        }

        Badge badge = badgeRepository.save(Badge.createBadge(player, request.badgeType()));
        return BadgeResponseDTO.from(badge);
    }
}
