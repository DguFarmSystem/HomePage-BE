package org.farmsystem.homepage.domain.user.dto.request;

import org.farmsystem.homepage.domain.user.entity.SocialType;

import java.util.Optional;

public record UserLoginRequestDTO(
        String code,
        SocialType socialType,
        Optional<String> studentNumber
) {
}
