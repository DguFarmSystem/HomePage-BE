package org.farmsystem.homepage.domain.user.dto.response;

import org.farmsystem.homepage.domain.user.entity.User;

public record UserInfoUpdateResponseDTO(
        String phoneNumber,
        String profileImageUrl
) {
    public static UserInfoUpdateResponseDTO from(User user) {
        return new UserInfoUpdateResponseDTO(user.getPhoneNumber(), user.getProfileImageUrl());
    }
}