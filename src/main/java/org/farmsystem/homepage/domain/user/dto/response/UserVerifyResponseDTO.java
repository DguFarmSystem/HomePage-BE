package org.farmsystem.homepage.domain.user.dto.response;

public record UserVerifyResponseDTO(
        Boolean isVerified,
        String name

) {
}
