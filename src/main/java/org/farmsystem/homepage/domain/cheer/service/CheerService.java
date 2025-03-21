package org.farmsystem.homepage.domain.cheer.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.cheer.dto.request.CheerRequestDTO;
import org.farmsystem.homepage.domain.cheer.dto.response.CheerResponseDTO;
import org.farmsystem.homepage.domain.cheer.dto.response.PagingCheerListResponseDTO;
import org.farmsystem.homepage.domain.cheer.entity.Cheer;
import org.farmsystem.homepage.domain.cheer.repository.CheerRepository;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheerService {

    private final CheerRepository cheerRepository;
    private final UserRepository userRepository;

    // 응원 리스트 조회 (페이징)
    public PagingCheerListResponseDTO getAllCheer(Pageable pageable) {
        Page<Cheer> cheerPage = cheerRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<CheerResponseDTO> cheerList = cheerPage.getContent().stream()
                .map(CheerResponseDTO::from)
                .collect(Collectors.toList());
        return PagingCheerListResponseDTO.of(cheerPage, pageable, cheerList);
    }

    // 응원 등록
    @Transactional
    public CheerResponseDTO createCheer(CheerRequestDTO request) {
        User cheerer = userRepository.findById(request.cheererId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        User cheered = userRepository.findById(request.cheeredId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Cheer cheer = Cheer.builder()
                .cheerer(cheerer)
                .cheered(cheered)
                .content(request.content())
                .tag(request.tag())
                .build();
        Cheer savedCheer = cheerRepository.save(cheer);
        return CheerResponseDTO.from(savedCheer);
    }
}
