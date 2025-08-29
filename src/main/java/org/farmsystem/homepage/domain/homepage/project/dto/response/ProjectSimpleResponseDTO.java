package org.farmsystem.homepage.domain.homepage.project.dto.response;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.homepage.project.entity.Project;

public record ProjectSimpleResponseDTO(
        Long projectId,
        String title,
        String introduction,
        String thumbnailImageUrl,
        Track track,
        int generation
) {
    public static ProjectSimpleResponseDTO fromEntity(Project project) {
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
