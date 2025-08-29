package org.farmsystem.homepage.domain.community.cheer.dto.response;

import org.farmsystem.homepage.domain.community.cheer.entity.Cheer;

import org.farmsystem.homepage.domain.common.dto.response.PageResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public record PagingCheerListResponseDTO(
        PageResponseDTO page,
        List<CheerResponseDTO> cheerList
) {
    public static PagingCheerListResponseDTO of(Page<Cheer> cheerPage, List<CheerResponseDTO> cheerList) {
        PageResponseDTO page = PageResponseDTO.of(cheerPage);
        return new PagingCheerListResponseDTO(page, cheerList);
    }
}
