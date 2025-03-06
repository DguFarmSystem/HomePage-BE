package org.farmsystem.homepage.domain.apply.dto.response;

import lombok.Builder;
import org.farmsystem.homepage.domain.common.entity.Track;

import java.time.LocalDateTime;

@Builder
public record ApplyListResponseDTO(
        Long applyId,
        String name,
        Track track,
        LocalDateTime updatedAt
) {
}
