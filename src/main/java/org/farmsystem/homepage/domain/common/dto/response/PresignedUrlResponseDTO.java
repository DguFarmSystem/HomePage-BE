package org.farmsystem.homepage.domain.common.dto.response;

public record PresignedUrlResponseDTO(
        String presignedUrl,
        String fileUrl
) {
    public static PresignedUrlResponseDTO of(String presignedUrl, String fileUrl) {
        return new PresignedUrlResponseDTO(presignedUrl, fileUrl);
    }
}
