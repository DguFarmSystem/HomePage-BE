package org.farmsystem.homepage.domain.user.dto.response;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.user.entity.User;

public record UserInfoResponseDTO(
        String name,
        String studentNumber,
        String profileImageUrl,
        String phoneNumber,
        String email,
        Track track,
        Integer generation,
        Integer currentSeed,
        Integer totalSeed
) {
    public static UserInfoResponseDTO from(User user) {
        return new UserInfoResponseDTO(
                user.getName(),
                user.getStudentNumber(),
                user.getProfileImageUrl(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getTrack(),
                user.getGeneration(),
                user.getCurrentSeed(),
                user.getTotalSeed()
        );
    }

}
