package org.farmsystem.homepage.domain.common.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

public record PageResponseDTO(

        @Schema(description = "페이지당 데이터 개수")
        int pageSize,
        @Schema(description = "총 데이터 개수")
        int totalElements,
        @Schema(description = "현재 페이지 데이터 개수")
        int currentPageElements,
        @Schema(description = "총 페이지 개수")
        int totalPages,
        @Schema(description = "현재 페이지")
        int currentPage,
        @Schema(description = "정렬 기준")
        String sortBy,
        @Schema(description = "다음 페이지 존재 여부")
        boolean hasNextPage,
        @Schema(description = "이전 페이지 존재 여부")
        boolean hasPreviousPage
) {
    public static PageResponseDTO of(Page<?> page) {
        return new PageResponseDTO(
                page.getSize(),
                (int) page.getTotalElements(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.getNumber()+1,
                page.getSort().toString(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
