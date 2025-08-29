package org.farmsystem.homepage.domain.community.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserTokenRequestDTO(
        @NotBlank
        String accessToken,
        @NotBlank
        String refreshToken
) {
}
