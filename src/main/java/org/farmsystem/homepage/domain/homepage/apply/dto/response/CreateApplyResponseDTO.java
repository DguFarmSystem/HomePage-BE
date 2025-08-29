package org.farmsystem.homepage.domain.homepage.apply.dto.response;

import lombok.Builder;

@Builder
public record CreateApplyResponseDTO(
        Long applyId
) {
}
