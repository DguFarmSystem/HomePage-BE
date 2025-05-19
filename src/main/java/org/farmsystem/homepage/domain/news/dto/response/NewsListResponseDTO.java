package org.farmsystem.homepage.domain.news.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.farmsystem.homepage.domain.news.entity.News;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "소식 목록 응답 DTO")
public record NewsListResponseDTO(
        @Schema(description = "소식 ID", example = "1")
        Long newsId,

        @Schema(description = "소식 제목", example = "5월 활동 요약")
        String title,

        @Schema(description = "썸네일 이미지 URL", example = "https://s3-bucket/thumbnail.jpg")
        String thumbnailUrl,

        @Schema(description = "본문 미리보기", example = "이번 달 파밍시스템 활동은... (최대 50자)")
        String contentPreview,

        @Schema(description = "태그 목록", example = "[\"#공지\", \"#이벤트\"]")
        List<String> tags,

        @Schema(description = "작성일", example = "2025-05-18T12:34:56")
        LocalDateTime createdAt,

        @Schema(description = "수정일", example = "2025-05-18T12:35:56")
        LocalDateTime updatedAt
) {
    public static NewsListResponseDTO fromEntity(News news) {
        return new NewsListResponseDTO(
                news.getNewsId(),
                news.getTitle(),
                news.getThumbnailUrl(),
                extractPreview(news.getContent()),
                news.getTags(),
                news.getCreatedAt(),
                news.getUpdatedAt()
        );
    }

    private static String extractPreview(String content) {
        if (content == null) return "";
        return content.length() <= 50 ? content : content.substring(0, 50) + "...";
    }
}
