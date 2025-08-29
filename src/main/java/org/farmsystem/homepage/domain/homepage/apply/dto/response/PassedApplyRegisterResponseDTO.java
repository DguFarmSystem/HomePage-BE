package org.farmsystem.homepage.domain.homepage.apply.dto.response;

import org.farmsystem.homepage.domain.homepage.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.common.entity.Track;

public record PassedApplyRegisterResponseDTO(
        String name,
        String major,
        String studentNumber,
        Track track,
        Integer generation,
        String phoneNumber,
        String notionAccount,
        String githubAccount
) {
    public static PassedApplyRegisterResponseDTO from(PassedApply passedApply) {
        return new PassedApplyRegisterResponseDTO(
                passedApply.getName(),
                passedApply.getMajor(),
                passedApply.getStudentNumber(),
                passedApply.getTrack(),
                passedApply.getGeneration(),
                passedApply.getPhoneNumber(),
                passedApply.getNotionAccount(),
                passedApply.getGithubAccount()
        );
    }
}
