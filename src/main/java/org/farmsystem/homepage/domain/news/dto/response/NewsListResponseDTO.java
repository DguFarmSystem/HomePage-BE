package org.farmsystem.homepage.domain.news.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.farmsystem.homepage.domain.news.entity.News;

@Schema(description = "소식 목록 응답 DTO")
public record NewsListResponseDTO(
        @Schema(description = "소식 ID", example = "1")
        Long newsId,

        @Schema(description = "소식 제목", example = "5월 활동 요약")
        String title,

        @Schema(description = "썸네일 이미지 URL", example = "https://s3-bucket/thumbnail.jpg")
        String thumbnailUrl
) {
    public static NewsListResponseDTO fromEntity(News news) {
        return new NewsListResponseDTO(news.getNewsId(), news.getTitle(), news.getThumbnailUrl());
    }
}
