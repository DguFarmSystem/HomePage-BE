package org.farmsystem.homepage.domain.common.dto.response;

import org.springframework.data.domain.Page;

public record PageResponseDTO(
        int totalElements, // 총 데이터 개수
        int pageSize, // 페이지당 데이터 개수
        int totalPages, // 총 페이지 개수
        int currentPage, // 현재 페이지
        String sortBy, // 정렬 기준
        boolean hasNextPage, // 다음 페이지 존재 여부
        boolean hasPreviousPage // 이전 페이지 존재 여부
) {
    public static PageResponseDTO of(Page<?> page) {
        return new PageResponseDTO(
                (int) page.getTotalElements(),
                page.getSize(),
                page.getTotalPages(),
                page.getNumber()+1,
                page.getSort().toString(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
