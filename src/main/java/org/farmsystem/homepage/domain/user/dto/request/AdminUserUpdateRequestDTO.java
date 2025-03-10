package org.farmsystem.homepage.domain.user.dto.request;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.user.entity.Role;
import org.farmsystem.homepage.domain.user.entity.User;

public record AdminUserUpdateRequestDTO(
        Role role,
        String name,
        String studentNumber,
        String notionAccount,
        String githubAccount,
        Track track,
        Integer generation
) {

    public User toEntity() {
        return User.builder()
                .role(role)
                .name(name)
                .studentNumber(studentNumber)
                .notionAccount(notionAccount)
                .githubAccount(githubAccount)
                .track(track)
                .generation(generation)
                .build();
    }
}
