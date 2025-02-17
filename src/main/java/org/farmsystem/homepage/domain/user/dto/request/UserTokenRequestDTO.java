package org.farmsystem.homepage.domain.user.dto.request;

public record UserTokenRequestDTO(
        String accessToken,
        String refreshToken
) {
}
