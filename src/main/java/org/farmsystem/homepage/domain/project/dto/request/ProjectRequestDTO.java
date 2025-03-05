package org.farmsystem.homepage.domain.project.dto.request;

import java.util.List;

public record ProjectRequestDTO(
        String title,
        String introduction,
        String content,
        String thumbnailImageUrl,
        String bodyImageUrl,
        String githubLink,
        String deploymentLink,
        String resourceLink,
        List<String> participants
) {}
