package org.farmsystem.homepage.domain.cheer.dto.response;

import org.farmsystem.homepage.domain.cheer.entity.Cheer;
import org.farmsystem.homepage.domain.common.dto.response.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record PagingCheerListResponseDTO(
        PageResponseDTO page,
        List<CheerResponseDTO> cheerList
) {
    public static PagingCheerListResponseDTO of(Page<Cheer> cheerPage, Pageable pageable, List<CheerResponseDTO> cheerList) {
        PageResponseDTO page = PageResponseDTO.of(cheerPage, pageable);
        return new PagingCheerListResponseDTO(page, cheerList);
    }
}
