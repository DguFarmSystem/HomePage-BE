package org.farmsystem.homepage.domain.user.dto.response;

import org.farmsystem.homepage.domain.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.user.entity.User;

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
