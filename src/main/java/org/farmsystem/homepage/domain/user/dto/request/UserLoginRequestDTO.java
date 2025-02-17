package org.farmsystem.homepage.domain.user.dto.request;

import org.farmsystem.homepage.domain.user.entity.SocialType;

public record UserLoginRequestDTO(
        String code,
        SocialType socialType,
        String studentNumber,
        String name

) {
}
