package org.farmsystem.homepage.domain.minigame.dex.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.dex.dto.DexDTO;
import org.farmsystem.homepage.domain.minigame.dex.entity.Dex;
import org.farmsystem.homepage.domain.minigame.dex.entity.PlantType;
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
    public DexDTO.DexResponse addDex(Long userId, DexDTO.DexRequest request) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        // 이미 등록된 식물인지 체크
        dexRepository.findByPlayerAndOwnedPlant(player, request.getOwnedPlant())
                .ifPresent(existing -> {
                    throw new BusinessException(ErrorCode.DEX_ALREADY_REGISTERED);
                });

        Dex savedDex = dexRepository.save(Dex.builder()
                .player(player)
                .ownedPlant(request.getOwnedPlant())
                .build());

        return DexDTO.DexResponse.builder()
                .dexId(savedDex.getDexId())
                .ownedPlant(savedDex.getOwnedPlant())
                .build();
    }

    @Transactional
    public DexDTO.DexListResponse getDexList(Long userId) {
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));

        List<DexDTO.DexResponse> list = dexRepository.findByPlayer(player)
                .stream()
                .map(dex -> DexDTO.DexResponse.builder()
                        .dexId(dex.getDexId())
                        .ownedPlant(dex.getOwnedPlant())
                        .build())
                .toList();

        return DexDTO.DexListResponse.builder()
                .dexList(list)
                .build();
    }
}
