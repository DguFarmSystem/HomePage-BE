package org.farmsystem.homepage.domain.user.dto.request;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.user.entity.Role;

public record AdminUserSearchRequestDTO(
        Track track,
        Integer generation,
        Role role,
        String major
) {
}
