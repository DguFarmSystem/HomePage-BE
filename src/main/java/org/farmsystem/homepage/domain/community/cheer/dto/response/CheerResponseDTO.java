package org.farmsystem.homepage.domain.community.cheer.dto.response;

import org.farmsystem.homepage.domain.community.cheer.entity.Cheer;
import org.farmsystem.homepage.domain.community.cheer.entity.CheerTag;

import java.time.LocalDateTime;

public record CheerResponseDTO(
        Long cheerId,
        CheerUserDTO cheerer,
        CheerUserDTO cheered,
        String content,
        CheerTag tag,
        LocalDateTime createdAt
) {
    public static CheerResponseDTO from(Cheer cheer) {
        return new CheerResponseDTO(
                cheer.getCheerId(),
                CheerUserDTO.from(cheer.getCheerer()),
                CheerUserDTO.from(cheer.getCheered()),
                cheer.getContent(),
                cheer.getTag(),
                cheer.getCreatedAt()
        );
    }
}
