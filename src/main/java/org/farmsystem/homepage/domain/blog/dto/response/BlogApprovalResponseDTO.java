package org.farmsystem.homepage.domain.blog.dto.response;

import org.farmsystem.homepage.domain.blog.entity.Blog;
import org.farmsystem.homepage.domain.user.entity.User;

import java.time.LocalDateTime;

public record BlogApprovalResponseDTO(
        Long blogId,
        String link,
        String approvalStatus,
        String approvedBy,
        LocalDateTime approvedAt
) {
    /**
     * Blog 엔티티와 관리자 엔티티를 BlogApprovalResponseDTO로 변환하는 정적 팩토리 메서드
     * @param blog 변환할 Blog 엔티티
     * @param admin 변환에 필요한 관리자 엔티티
     * @return BlogApprovalResponseDTO 객체
     */
    public static BlogApprovalResponseDTO fromEntity(Blog blog, User admin) {
        return new BlogApprovalResponseDTO(
                blog.getBlogId(),
                blog.getLink(),
                blog.getApprovalStatus().name(),
                admin.getName(),
                blog.getApprovedAt()
        );
    }
}
