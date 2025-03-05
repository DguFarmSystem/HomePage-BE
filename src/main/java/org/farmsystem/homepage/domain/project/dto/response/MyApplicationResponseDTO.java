package org.farmsystem.homepage.domain.project.dto.response;

public record MyApplicationResponseDTO(
        Long projectId,
        String title,
        String approvalStatus
) {}
