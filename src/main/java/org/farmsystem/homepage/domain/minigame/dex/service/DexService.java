package org.farmsystem.homepage.domain.minigame.dex.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.dex.dto.request.DexRequest;
import org.farmsystem.homepage.domain.minigame.dex.dto.response.DexListResponse;
import org.farmsystem.homepage.domain.minigame.dex.dto.response.DexResponse;
import org.farmsystem.homepage.domain.minigame.dex.entity.Dex;
import org.farmsystem.homepage.domain.minigame.dex.repository.DexRepository;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.player.repository.PlayerRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DexService {

    private final PlayerRepository playerRepository;
    private final DexRepository dexRepository;

    @Transactional
    public DexResponse addDex(Long userId, DexRequest request) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        // 이미 등록된 식물인지 체크
        dexRepository.findByPlayerAndOwnedPlant(player, request.ownedPlant())
                .ifPresent(existing -> {
                    throw new BusinessException(ErrorCode.DEX_ALREADY_REGISTERED);
                });

        Dex savedDex = dexRepository.save(Dex.builder()
                .player(player)
                .ownedPlant(request.ownedPlant())
                .build());

        return new DexResponse(savedDex.getDexId(), savedDex.getOwnedPlant());
    }

    @Transactional
    public DexListResponse getDexList(Long userId) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        List<DexResponse> list = dexRepository.findByPlayer(player)
                .stream()
                .map(dex -> new DexResponse(dex.getDexId(), dex.getOwnedPlant()))
                .toList();

        return new DexListResponse(list);
    }
}
