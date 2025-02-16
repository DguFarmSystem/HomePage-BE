package org.farmsystem.homepage.domain.user.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record UserInfoUpdateRequestDTO(
        String phoneNumber,
        MultipartFile profileImage
) {
}