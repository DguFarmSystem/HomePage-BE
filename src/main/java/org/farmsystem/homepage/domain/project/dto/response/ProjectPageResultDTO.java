package org.farmsystem.homepage.domain.project.dto.response;

import org.farmsystem.homepage.domain.common.dto.response.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record ProjectPageResultDTO<T>(
        List<T> content,
        PageResponseDTO pageInfo
) {
    public static <T> ProjectPageResultDTO<T> of(Page<T> page, Pageable pageable) {
        return new ProjectPageResultDTO<>(
                page.getContent(),
                PageResponseDTO.of(page, pageable)
        );
    }
}
