package org.farmsystem.homepage.domain.news.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.farmsystem.homepage.domain.news.entity.News;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "소식 상세 응답 DTO")
public record NewsDetailResponseDTO(
        Long newsId,
        String title,
        String thumbnailUrl,
        String content,
        List<String> imageUrls,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static NewsDetailResponseDTO fromEntity(News news) {
        return new NewsDetailResponseDTO(
                news.getNewsId(),
                news.getTitle(),
                news.getThumbnailUrl(),
                news.getContent(),
                news.getImageUrls(),
                news.getTags(),
                news.getCreatedAt(),
                news.getUpdatedAt()
        );
    }
}
