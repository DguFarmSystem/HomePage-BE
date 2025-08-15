package org.farmsystem.homepage.domain.minigame.player.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.player.dto.request.BadgeUpdateRequest;
import org.farmsystem.homepage.domain.minigame.player.dto.response.BadgeResponse;
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

    @Transactional(readOnly = true)
    public List<BadgeResponse> getBadges(Long userId) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        return badgeRepository.findByPlayer(player)
                .stream()
                .map(badge -> new BadgeResponse(
                        badge.getBadgeId(),
                        badge.getBadgeType()
                ))
                .toList();
    }

    @Transactional
    public BadgeResponse addBadge(Long userId, BadgeUpdateRequest request) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        Badge badge = Badge.builder()
                .badgeType(request.badgeType())
                .player(player)
                .build();

        badgeRepository.save(badge);

        return new BadgeResponse(badge.getBadgeId(), badge.getBadgeType());
    }
}
