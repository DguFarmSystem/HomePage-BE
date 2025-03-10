package org.farmsystem.homepage.domain.common.dto.response;

public record PresignedUrlResponseDTO(
        String presignedUrl
) {
    public static PresignedUrlResponseDTO of(String presignedUrl) {
        return new PresignedUrlResponseDTO(presignedUrl);
    }
}
