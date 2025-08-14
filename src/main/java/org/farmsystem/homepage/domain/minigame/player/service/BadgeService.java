package org.farmsystem.homepage.domain.minigame.player.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.dto.BadgeDTO;
import org.farmsystem.homepage.domain.minigame.player.dto.BadgeDTO.BadgeUpdateRequest;
import org.farmsystem.homepage.domain.minigame.player.entity.Badge;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.player.repository.BadgeRepository;
import org.farmsystem.homepage.domain.minigame.player.repository.PlayerRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public List<BadgeDTO.BadgeResponse> getBadges(Long userId) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        return badgeRepository.findByPlayer(player)
                .stream()
                .map(badge -> BadgeDTO.BadgeResponse.builder()
                        .badgeId(badge.getBadgeId())
                        .badgeType(badge.getBadgeType())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public BadgeDTO.BadgeResponse addBadge(Long userId, BadgeUpdateRequest request) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        Badge badge = Badge.builder()
                .badgeType(request.getBadgeType())
                .player(player)
                .build();

        badgeRepository.save(badge);

        return BadgeDTO.BadgeResponse.builder()
                .badgeId(badge.getBadgeId())
                .badgeType(badge.getBadgeType())
                .build();
    }
}
