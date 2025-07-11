package org.farmsystem.homepage.domain.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "blog")
public class Blog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection(targetClass = BlogCategory.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "blog_category_mapping", joinColumns = @JoinColumn(name = "blog_id"))
    @Column(name = "category")
    private Set<BlogCategory> categories;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    /**
     * 관리자 관련 코드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    private LocalDateTime approvedAt;

    public void approve(User admin) {
        if (this.approvalStatus != ApprovalStatus.PENDING) {
            throw new BusinessException(ErrorCode.ALREADY_ACCEPTED);
        }
        this.approvalStatus = ApprovalStatus.APPROVED;
        this.approvedBy = admin;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject(User admin) {
        if (this.approvalStatus != ApprovalStatus.PENDING) {
            throw new BusinessException(ErrorCode.ALREADY_ACCEPTED);
        }
        this.approvalStatus = ApprovalStatus.REJECTED;
        this.approvedBy = admin;
        this.approvedAt = LocalDateTime.now();
    }

}
