package org.farmsystem.homepage.domain.project.dto.response;

import org.farmsystem.homepage.domain.common.entity.Track;

public record ProjectSimpleResponseDTO(
        Long projectId,
        String title,
        String introduction,
        String thumbnailImageUrl,
        Track track,
        Integer generation
) {}
