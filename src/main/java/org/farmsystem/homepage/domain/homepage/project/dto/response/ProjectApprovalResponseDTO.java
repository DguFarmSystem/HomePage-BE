package org.farmsystem.homepage.domain.homepage.project.dto.response;

import java.time.LocalDateTime;

public record ProjectApprovalResponseDTO(
        Long projectId,
        String title,
        String approvalStatus,
        String approvedBy,
        LocalDateTime approvedAt
) {}
