package org.farmsystem.homepage.domain.community.user.dto.response;

import org.farmsystem.homepage.domain.homepage.apply.entity.PassedApply;

public record UserVerifyResponseDTO(
        Boolean isVerified,
        String studentNumber,
        String name

) {
    public static UserVerifyResponseDTO from(Boolean isVerified, PassedApply verifiedUser) {
        return new UserVerifyResponseDTO(
                isVerified,
                verifiedUser.getStudentNumber(),
                verifiedUser.getName()
        );
    }
}
