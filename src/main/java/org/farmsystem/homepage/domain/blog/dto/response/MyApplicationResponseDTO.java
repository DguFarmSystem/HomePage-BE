package org.farmsystem.homepage.domain.blog.dto.response;

public record MyApplicationResponseDTO(
        Long blogId,
        String link,
        String approvalStatus
) {}
