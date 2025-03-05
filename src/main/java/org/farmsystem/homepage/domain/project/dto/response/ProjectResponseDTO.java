package org.farmsystem.homepage.domain.project.dto.response;

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
        String approvalStatus
) {}
