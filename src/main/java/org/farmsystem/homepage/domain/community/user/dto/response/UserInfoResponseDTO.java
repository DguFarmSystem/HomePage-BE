package org.farmsystem.homepage.domain.community.user.dto.response;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.community.user.entity.Role;
import org.farmsystem.homepage.domain.community.user.entity.User;

public record UserInfoResponseDTO(
        Long userId,
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
        Integer totalSeed
) {
    public static UserInfoResponseDTO from(User user) {
        return new UserInfoResponseDTO(
                user.getUserId(),
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
                user.getTotalSeed()
        );
    }
}
