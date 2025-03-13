package org.farmsystem.homepage.domain.apply.dto.response;

import lombok.Builder;
import org.farmsystem.homepage.domain.apply.entity.Apply;
import org.farmsystem.homepage.domain.common.entity.Track;

import java.time.LocalDateTime;

@Builder
public record ApplyListResponseDTO(
        Long applyId,
        String name,
        Track track,
        LocalDateTime updatedAt
) {
    public static ApplyListResponseDTO from(Apply apply) {
        return ApplyListResponseDTO.builder()
                .applyId(apply.getApplyId())
                .name(apply.getName())
                .track(apply.getTrack())
                .updatedAt(apply.getUpdatedAt())
                .build();
    }
}
