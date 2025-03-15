package org.farmsystem.homepage.domain.cheer.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.cheer.dto.response.CheerResponseDTO;
import org.farmsystem.homepage.domain.cheer.dto.response.PagingCheerListResponseDTO;
import org.farmsystem.homepage.domain.cheer.entity.Cheer;
import org.farmsystem.homepage.domain.cheer.repository.CheerRepository;
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

    // 응원 리스트 조회 (페이징)
    public PagingCheerListResponseDTO getAllCheer(Pageable pageable) {
        Page<Cheer> cheerPage = cheerRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<CheerResponseDTO> cheerList = cheerPage.getContent().stream()
                .map(CheerResponseDTO::from)
                .collect(Collectors.toList());
        return PagingCheerListResponseDTO.of(cheerPage, pageable, cheerList);
    }
}
