package org.farmsystem.homepage.domain.homepage.blog.dto.response;

public record MyApplicationResponseDTO(
        Long blogId,
        String link,
        String approvalStatus
) {}
