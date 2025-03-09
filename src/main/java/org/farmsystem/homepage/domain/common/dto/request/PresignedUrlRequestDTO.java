package org.farmsystem.homepage.domain.common.dto.request;

public record PresignedUrlRequestDTO(
        String directory,
        String fileName
) {
}
