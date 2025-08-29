package org.farmsystem.homepage.domain.homepage.blog.dto.response;

import java.time.LocalDateTime;

public record PendingBlogResponseDTO(
        Long blogId,
        String link,
        String userNickname,
        LocalDateTime appliedAt
) {}
