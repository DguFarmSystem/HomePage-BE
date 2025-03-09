package org.farmsystem.homepage.domain.user.dto.request;

import org.farmsystem.homepage.domain.user.entity.User;

public record UserUpdateRequestDTO(
        String phoneNumber,
        String profileImageUrl,
        String major
) {
    public User toEntity() {
        return User.builder()
                .phoneNumber(phoneNumber)
                .profileImageUrl(profileImageUrl)
                .major(major)
                .build();
    }
}