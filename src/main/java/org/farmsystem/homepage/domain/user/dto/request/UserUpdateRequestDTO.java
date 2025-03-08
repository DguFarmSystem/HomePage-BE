package org.farmsystem.homepage.domain.user.dto.request;

import org.farmsystem.homepage.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

public record UserUpdateRequestDTO(
        String phoneNumber,
        MultipartFile profileImage,
        String major
) {
    public User toEntity(String profileImageUrl) {
        return User.builder()
                .phoneNumber(phoneNumber)
                .profileImageUrl(profileImageUrl)
                .major(major)
                .build();
    }
}