package org.farmsystem.homepage.domain.user.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record UserInfoUpdateRequestDTO(
        Optional<String> phoneNumber,
        Optional<MultipartFile> profileImage,
        Optional<String> major
) {
}