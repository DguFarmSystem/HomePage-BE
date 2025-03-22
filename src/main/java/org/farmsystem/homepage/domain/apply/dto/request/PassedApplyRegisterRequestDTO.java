package org.farmsystem.homepage.domain.apply.dto.request;

import org.farmsystem.homepage.domain.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.common.entity.Track;

public record PassedApplyRegisterRequestDTO(
        String name,
        String studentNumber,
        String major,
        String phoneNumber,
        String notionAccount,
        String githubAccount,
        Track track,
        Integer generation
) {
    public PassedApply toEntity() {
        return PassedApply.builder()
                .name(name)
                .studentNumber(studentNumber)
                .major(major)
                .phoneNumber(phoneNumber)
                .notionAccount(notionAccount)
                .githubAccount(githubAccount)
                .track(track)
                .generation(generation)
                .build();
    }
}
