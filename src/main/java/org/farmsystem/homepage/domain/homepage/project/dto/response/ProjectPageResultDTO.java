package org.farmsystem.homepage.domain.homepage.project.dto.response;

import org.farmsystem.homepage.domain.common.dto.response.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public record ProjectPageResultDTO<T>(
        List<T> content,
        PageResponseDTO pageInfo
) {
    public static <T> ProjectPageResultDTO<T> of(Page<T> page, PageRequest pageRequest) {
        return new ProjectPageResultDTO<>(
                page.getContent(),
                PageResponseDTO.of(page)
        );
    }
}
