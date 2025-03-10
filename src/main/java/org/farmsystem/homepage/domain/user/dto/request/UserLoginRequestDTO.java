package org.farmsystem.homepage.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.farmsystem.homepage.domain.user.entity.SocialType;

import java.util.Optional;

public record UserLoginRequestDTO(
        @NotBlank
        String code,
        @NotBlank
        SocialType socialType,
        Optional<String> studentNumber
) {
}
