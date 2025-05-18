package org.farmsystem.homepage.domain.news.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.farmsystem.homepage.domain.news.entity.News;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "소식 상세 응답 DTO")
public record NewsDetailResponseDTO(
        @Schema(description = "소식 ID", example = "1")
        Long newsId,

        @Schema(description = "소식 제목", example = "5월 활동 요약")
        String title,

        @Schema(description = "썸네일 이미지 URL", example = "https://s3-bucket/thumbnail.jpg")
        String thumbnailUrl,

        @Schema(description = "소식 본문", example = "파밍 시스템 5월 활동 보고입니다.")
        String content,

        @Schema(description = "본문 이미지 URL 목록", example = "[\"https://s3/image1.jpg\", \"https://s3/image2.jpg\"]")
        List<String> imageUrls,

        @Schema(description = "작성일", example = "2025-05-18T15:30:00")
        LocalDateTime createdAt,

        @Schema(description = "수정일", example = "2025-05-18T15:45:00")
        LocalDateTime updatedAt
) {
    public static NewsDetailResponseDTO fromEntity(News news) {
        return new NewsDetailResponseDTO(
                news.getNewsId(),
                news.getTitle(),
                news.getThumbnailUrl(),
                news.getContent(),
                news.getImageUrls(),
                news.getCreatedAt(),
                news.getUpdatedAt()
        );
    }
}
