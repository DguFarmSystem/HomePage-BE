package org.farmsystem.homepage.domain.blog.dto.response;

import org.farmsystem.homepage.domain.blog.entity.Blog;

public record MyApplicationResponseDTO(
        Long blogId,
        String link,
        String approvalStatus
) {
    public static MyApplicationResponseDTO fromEntity(Blog blog) {
        return new MyApplicationResponseDTO(
                blog.getBlogId(),
                blog.getLink(),
                blog.getApprovalStatus().name()
        );
    }
}
