package org.farmsystem.homepage.domain.user.dto.response;

public record UserVerifyResponseDTO(
        Boolean isVerified,
        String name
) {
    public static UserVerifyResponseDTO from(Boolean isVerified, String name) {
        return new UserVerifyResponseDTO(isVerified, name);
    }
}
