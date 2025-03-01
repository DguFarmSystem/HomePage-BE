package org.farmsystem.homepage.domain.apply.dto.response;

import lombok.Builder;

@Builder
public record CreateApplyResponseDTO(
        Long applyId
) {
}
