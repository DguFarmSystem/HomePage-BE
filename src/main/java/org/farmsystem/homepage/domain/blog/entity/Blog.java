package org.farmsystem.homepage.domain.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.blog.dto.request.BlogRequestDTO;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy; // 수락한 관리자

    private LocalDateTime approvedAt;

    // 관리자 생성
    private Blog(BlogRequestDTO request, User admin) {
        this.link = request.link();
        this.categories = request.categories();
        this.user = admin;
        this.approvalStatus = ApprovalStatus.APPROVED;
        this.approvedBy = admin;
        this.approvedAt = LocalDateTime.now();
    }

    // 사용자 신청
    private Blog(BlogRequestDTO request, User user, ApprovalStatus status) {
        this.link = request.link();
        this.categories = request.categories();
        this.user = user;
        this.approvalStatus = status; // PENDING 상태로 설정
    }

    public static Blog createByAdmin(BlogRequestDTO request, User admin) {
        return new Blog(request, admin);
    }

    public static Blog apply(BlogRequestDTO request, User user) {
        return new Blog(request, user, ApprovalStatus.PENDING);
    }

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
