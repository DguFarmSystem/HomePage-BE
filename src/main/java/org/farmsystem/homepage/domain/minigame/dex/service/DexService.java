package org.farmsystem.homepage.domain.minigame.dex.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.dex.dto.request.DexRequestDTO;
import org.farmsystem.homepage.domain.minigame.dex.dto.response.DexResponseDTO;
import org.farmsystem.homepage.domain.minigame.dex.entity.Dex;
import org.farmsystem.homepage.domain.minigame.dex.repository.DexRepository;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Store;
import org.farmsystem.homepage.domain.minigame.inventory.repository.StoreRepository;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.farmsystem.homepage.domain.minigame.player.repository.PlayerRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.hibernate.type.TrueFalseConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DexService {

    private final PlayerRepository playerRepository;
    private final DexRepository dexRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public DexResponseDTO addDex(Long userId, DexRequestDTO request) {
        // 플레이어 조회
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));
        // 해당 식물이 store에 존재하는지 확인
        Store store = storeRepository.findByStoreGoodsNumber(request.ownedPlant())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        // 이미 등록된 식물인지 확인
        if (dexRepository.existsByPlayerAndOwnedPlant(player, store)) {
            throw new BusinessException(ErrorCode.DEX_ALREADY_REGISTERED);
        }

        Dex savedDex = dexRepository.save(Dex.createDex(player, store));
        return DexResponseDTO.from(savedDex);
    }

    @Transactional(readOnly= true)
    public List<DexResponseDTO> getDexList(Long userId) {
        // 플레이어 조회
        Player player = playerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PLAYER_NOT_FOUND));
        // 도감 목록 조회 및 반환
        return dexRepository.findByPlayer(player)
                .stream()
                .map(DexResponseDTO::from)
                .toList();
    }
}
