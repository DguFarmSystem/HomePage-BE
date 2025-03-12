package org.farmsystem.homepage.domain.user.dto.response;

import org.farmsystem.homepage.domain.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.common.entity.Track;

public record UserRegisterResponseDTO(
        Long passedApplyId,
        String name,
        String studentNumber,
        String major,
        String phoneNumber,
        String notionAccount,
        String githubAccount,
        Track track,
        Integer generation
) {
    public static UserRegisterResponseDTO from(PassedApply passedApply) {
        return new UserRegisterResponseDTO(
                passedApply.getPassedApplyId(),
                passedApply.getName(),
                passedApply.getStudentNumber(),
                passedApply.getMajor(),
                passedApply.getPhoneNumber(),
                passedApply.getNotionAccount(),
                passedApply.getGithubAccount(),
                passedApply.getTrack(),
                passedApply.getGeneration()
        );
    }
}
