package org.farmsystem.homepage.domain.project.dto.response;

import org.farmsystem.homepage.domain.common.entity.Track;

import java.util.List;

public record ProjectResponseDTO(
        Long projectId,
        String title,
        String introduction,
        String content,
        String thumbnailImageUrl,
        String bodyImageUrl,
        String githubLink,
        String deploymentLink,
        String resourceLink,
        List<String> participants,
        String approvalStatus,
        Track track
) {}