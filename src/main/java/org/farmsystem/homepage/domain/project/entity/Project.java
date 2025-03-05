package org.farmsystem.homepage.domain.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project")
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String content;

    private String thumbnailImageUrl;
    private String bodyImageUrl;

    private String githubLink;
    private String deploymentLink;
    private String resourceLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    @CollectionTable(name = "project_participants", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "participant_info")
    private List<String> participants;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Column(nullable = false)
    private Integer generation;

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
