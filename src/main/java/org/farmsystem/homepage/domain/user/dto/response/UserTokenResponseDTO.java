package org.farmsystem.homepage.domain.user.dto.response;

public record UserTokenResponseDTO(
        String accessToken,
        String refreshToken
) { }