package org.farmsystem.homepage.domain.common.dto.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public record PageResponseDTO(
        int totalElements,
        int pageSize,
        int totalPages,
        int currentPage,
        String sortBy
) {
    public static PageResponseDTO of(Page<?> page, Pageable pageable) {
        return new PageResponseDTO(
                (int) page.getTotalElements(),
                pageable.getPageSize(),
                page.getTotalPages(),
                pageable.getPageNumber() + 1,
                pageable.getSort().toString()
        );
    }
}
