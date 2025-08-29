package org.farmsystem.homepage.domain.community.user.dto.request;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.community.user.entity.Role;
import org.farmsystem.homepage.domain.community.user.entity.User;

public record AdminUserUpdateRequestDTO(
        Role role,
        String name,
        String studentNumber,
        String major,
        Track track,
        Integer generation
) {

    public User toEntity() {
        return User.builder()
                .role(role)
                .name(name)
                .studentNumber(studentNumber)
                .major(major)
                .track(track)
                .generation(generation)
                .build();
    }
}
