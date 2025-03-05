package org.farmsystem.homepage.domain.project.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.project.dto.response.ProjectResponseDTO;
import org.farmsystem.homepage.domain.project.dto.response.ProjectApprovalResponseDTO;
import org.farmsystem.homepage.domain.project.entity.Project;
import org.farmsystem.homepage.domain.project.entity.ApprovalStatus;
import org.farmsystem.homepage.domain.project.repository.ProjectRepository;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.farmsystem.homepage.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectApprovalResponseDTO approveProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND));

        User admin = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        project.approve(admin);

        return new ProjectApprovalResponseDTO(
                project.getProjectId(),
                project.getTitle(),
                project.getApprovalStatus().name(),
                admin.getName(),
                project.getApprovedAt()
        );
    }

    public ProjectApprovalResponseDTO rejectProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND));

        User admin = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        project.reject(admin);

        return new ProjectApprovalResponseDTO(
                project.getProjectId(),
                project.getTitle(),
                project.getApprovalStatus().name(),
                admin.getName(),
                project.getApprovedAt()
        );
    }

    /**
     * 관리자 - 승인 대기 중인 프로젝트 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getPendingProjects() {
        return projectRepository.findByApprovalStatus(ApprovalStatus.PENDING).stream()
                .map(project -> new ProjectResponseDTO(
                        project.getProjectId(),
                        project.getTitle(),
                        project.getIntroduction(),
                        project.getContent(),
                        project.getThumbnailImageUrl(),
                        project.getBodyImageUrl(),
                        project.getGithubLink(),
                        project.getDeploymentLink(),
                        project.getResourceLink(),
                        project.getParticipants(),
                        project.getApprovalStatus().name()
                )).toList();
    }
}
