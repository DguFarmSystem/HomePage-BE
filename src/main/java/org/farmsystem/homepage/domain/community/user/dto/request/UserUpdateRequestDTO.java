package org.farmsystem.homepage.domain.community.user.dto.request;

import org.farmsystem.homepage.domain.community.user.entity.User;

public record UserUpdateRequestDTO(
        String profileImageUrl,
        String phoneNumber,
        String notionAccount,
        String githubAccount
) {
    public User toEntity() {
        return User.builder()
                .profileImageUrl(profileImageUrl)
                .phoneNumber(phoneNumber)
                .notionAccount(notionAccount)
                .githubAccount(githubAccount)
                .build();
    }
}