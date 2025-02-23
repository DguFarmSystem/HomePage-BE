package org.farmsystem.homepage.domain.user.dto.response;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.user.entity.Role;
import org.farmsystem.homepage.domain.user.entity.User;

public record UserInfoResponseDTO(
        Role role,
        String name,
        String studentNumber,
        String major,
        String profileImageUrl,
        String phoneNumber,
        String notionAccount,
        String githubAccount,
        Track track,
        Integer generation,
        Integer currentSeed,
        Integer totalSeed
) {
    public static UserInfoResponseDTO from(User user) {
        return new UserInfoResponseDTO(
                user.getRole(),
                user.getName(),
                user.getStudentNumber(),
                user.getMajor(),
                user.getProfileImageUrl(),
                user.getPhoneNumber(),
                user.getNotionAccount(),
                user.getGithubAccount(),
                user.getTrack(),
                user.getGeneration(),
                user.getCurrentSeed(),
                user.getTotalSeed()
        );
    }

}
