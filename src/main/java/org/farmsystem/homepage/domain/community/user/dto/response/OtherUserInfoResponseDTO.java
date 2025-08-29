package org.farmsystem.homepage.domain.community.user.dto.response;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.community.user.entity.User;


public record OtherUserInfoResponseDTO(
        String profileImageUrl,
        String name,
        Track track,
        Integer generation,
        String major,
        String githubAccount

) {
    public static OtherUserInfoResponseDTO from(User user) {
        return new OtherUserInfoResponseDTO(
                user.getProfileImageUrl(),
                user.getName(),
                user.getTrack(),
                user.getGeneration(),
                user.getMajor(),
                user.getGithubAccount()
        );
    }
}
