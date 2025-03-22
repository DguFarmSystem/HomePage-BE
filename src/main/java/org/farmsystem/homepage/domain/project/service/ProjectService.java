package org.farmsystem.homepage.domain.project.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.project.dto.request.ProjectRequestDTO;
import org.farmsystem.homepage.domain.project.dto.response.ProjectResponseDTO;
import org.farmsystem.homepage.domain.project.dto.response.ProjectSimpleResponseDTO;
import org.farmsystem.homepage.domain.project.entity.ApprovalStatus;
import org.farmsystem.homepage.domain.project.entity.Project;
import org.farmsystem.homepage.domain.project.repository.ProjectRepository;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.farmsystem.homepage.global.error.ErrorCode.PROJECT_NOT_FOUND;
import static org.farmsystem.homepage.global.error.ErrorCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
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
                .track(user.getTrack())
                .participants(request.participants())
                .approvalStatus(ApprovalStatus.PENDING)
                .build();

        projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getMyProjects(Long userId) {
        return projectRepository.findByUser_UserId(userId).stream()
                .map(this::toDTO)
                .toList();
    }


    public Page<ProjectSimpleResponseDTO> getFilteredProjects(Integer generation, Track track, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects;

        if (generation != null && track != null) {
            projects = projectRepository.findByApprovalStatusAndGenerationAndTrack(ApprovalStatus.APPROVED, generation, track, pageable);
        } else if (generation != null) {
            projects = projectRepository.findByApprovalStatusAndGeneration(ApprovalStatus.APPROVED, generation, pageable);
        } else if (track != null) {
            projects = projectRepository.findByApprovalStatusAndTrack(ApprovalStatus.APPROVED, track, pageable);
        } else {
            projects = projectRepository.findByApprovalStatus(ApprovalStatus.APPROVED, pageable);
        }

        return projects.map(this::toSimpleDTO);
    }

    @Transactional(readOnly = true)
    public ProjectResponseDTO getProjectDetail(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND));

        return toDTO(project);
    }




    private ProjectResponseDTO toDTO(Project project) {
        return new ProjectResponseDTO(
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
                project.getApprovalStatus().name(),
                project.getTrack()
        );
    }

    private ProjectSimpleResponseDTO toSimpleDTO(Project project) {
        return new ProjectSimpleResponseDTO(
                project.getProjectId(),
                project.getTitle(),
                project.getIntroduction(),
                project.getThumbnailImageUrl(),
                project.getTrack(),
                project.getGeneration()
        );
    }

}
