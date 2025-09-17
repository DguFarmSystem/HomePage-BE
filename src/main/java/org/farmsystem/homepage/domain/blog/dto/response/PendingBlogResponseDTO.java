package org.farmsystem.homepage.domain.blog.dto.response;

import org.farmsystem.homepage.domain.blog.entity.Blog;

import java.time.LocalDateTime;

public record PendingBlogResponseDTO(
        Long blogId,
        String link,
        String userNickname,
        LocalDateTime appliedAt
) {
    public static PendingBlogResponseDTO fromEntity(Blog blog) {
        return new PendingBlogResponseDTO(
                blog.getBlogId(),
                blog.getLink(),
                blog.getUser().getName(),
                blog.getCreatedAt()
        );
    }
}