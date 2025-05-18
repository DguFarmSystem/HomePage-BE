package org.farmsystem.homepage.domain.news.dto.response;

import org.farmsystem.homepage.domain.news.entity.News;

import java.util.List;

public record NewsDetailResponseDTO(Long newsId, String title, String thumbnailUrl, String content, List<String> imageUrls) {
    public static NewsDetailResponseDTO fromEntity(News news) {
        return new NewsDetailResponseDTO(
                news.getNewsId(),
                news.getTitle(),
                news.getThumbnailUrl(),
                news.getContent(),
                news.getImageUrls()
        );
    }
}
