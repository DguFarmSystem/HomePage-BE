package org.farmsystem.homepage.domain.project.dto.response;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.project.entity.Project;

import java.util.List;

public record ProjectResponseDTO(
        Long projectId,
        String title,
        String introduction,
        String content,
        String thumbnailImageUrl,
        String bodyImageUrl,
        String githubLink,
        String deploymentLink,
        String resourceLink,
        List<String> participants,
        String approvalStatus,
        Track track
) {
    public static ProjectResponseDTO fromEntity(Project project) {
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
}
