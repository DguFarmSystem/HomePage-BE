package org.farmsystem.homepage.domain.news.dto.response;

import org.farmsystem.homepage.domain.news.entity.News;

public record NewsListResponseDTO(Long newsId, String title, String thumbnailUrl) {
    public static NewsListResponseDTO fromEntity(News news) {
        return new NewsListResponseDTO(news.getNewsId(), news.getTitle(), news.getThumbnailUrl());
    }
}
