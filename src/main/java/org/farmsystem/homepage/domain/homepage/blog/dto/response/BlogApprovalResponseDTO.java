package org.farmsystem.homepage.domain.homepage.blog.dto.response;

import java.time.LocalDateTime;

public record BlogApprovalResponseDTO(
        Long blogId,
        String link,
        String approvalStatus,
        String approvedBy,
        LocalDateTime approvedAt
) {}
