package org.farmsystem.homepage.domain.project.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.project.dto.request.ProjectRequestDTO;
import org.farmsystem.homepage.domain.project.dto.response.ProjectResponseDTO;
import org.farmsystem.homepage.domain.project.dto.response.MyApplicationResponseDTO;
import org.farmsystem.homepage.domain.project.entity.Project;
import org.farmsystem.homepage.domain.project.entity.ApprovalStatus;
import org.farmsystem.homepage.domain.project.repository.ProjectRepository;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.farmsystem.homepage.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void applyForProject(Long userId, ProjectRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        Project project = Project.builder()
                .title(request.title())
                .introduction(request.introduction())
                .content(request.content())
                .thumbnailImageUrl(request.thumbnailImageUrl())
                .bodyImageUrl(request.bodyImageUrl())
                .githubLink(request.githubLink())
                .deploymentLink(request.deploymentLink())
                .resourceLink(request.resourceLink())
                .user(user)
                .generation(user.getGeneration())
                .participants(request.participants())
                .approvalStatus(ApprovalStatus.PENDING)
                .build();

        projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getApprovedProjects() {
        return projectRepository.findByApprovalStatus(ApprovalStatus.APPROVED).stream()
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

    @Transactional(readOnly = true)
    public List<MyApplicationResponseDTO> getMyProjects(Long userId) {
        return projectRepository.findByUser_UserId(userId).stream()
                .map(project -> new MyApplicationResponseDTO(
                        project.getProjectId(),
                        project.getTitle(),
                        project.getApprovalStatus().name()
                )).toList();
    }

    @Transactional(readOnly = true)
    public Page<ProjectResponseDTO> getApprovedProjectsPaged(int page, int size) {
        Page<Project> approvedProjects = projectRepository.findByApprovalStatus(ApprovalStatus.APPROVED, PageRequest.of(page, size));
        return approvedProjects.map(project -> new ProjectResponseDTO(
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
        ));
    }

    /**
     * 특정 기수의 승인된 프로젝트 조회 (페이징)
     */
    @Transactional(readOnly = true)
    public Page<ProjectResponseDTO> getProjectsByGeneration(Integer generation, int page, int size) {
        Page<Project> projects = projectRepository.findByApprovalStatusAndGeneration(
                ApprovalStatus.APPROVED, generation, PageRequest.of(page, size));

        return projects.map(project -> new ProjectResponseDTO(
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
        ));
    }
}
