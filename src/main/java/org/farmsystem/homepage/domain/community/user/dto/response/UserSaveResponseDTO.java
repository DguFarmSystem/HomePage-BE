package org.farmsystem.homepage.domain.community.user.dto.response;

import org.farmsystem.homepage.domain.community.user.entity.SocialType;
import org.farmsystem.homepage.domain.homepage.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.community.user.entity.Role;
import org.farmsystem.homepage.domain.community.user.entity.User;

public record UserSaveResponseDTO(
        String profileImageUrl,
        String name,
        String studentNumber,
        SocialType socialType,
        String major,
        String phoneNumber,
        String notionAccount,
        String githubAccount,
        Track track,
        Integer generation,
        Role role
) {

    public static User fromPassedUser(PassedApply passedUser, SocialType socialType,String socialId, String imageUrl) {
        return User.builder()
                .profileImageUrl(imageUrl)
                .name(passedUser.getName())
                .studentNumber(passedUser.getStudentNumber())
                .socialId(socialId)
                .socialType(socialType)
                .major(passedUser.getMajor())
                .phoneNumber(passedUser.getPhoneNumber())
                .notionAccount(passedUser.getNotionAccount())
                .githubAccount(passedUser.getGithubAccount())
                .track(passedUser.getTrack())
                .generation(passedUser.getGeneration())
                .role(Role.USER)
                .build();
    }
}
