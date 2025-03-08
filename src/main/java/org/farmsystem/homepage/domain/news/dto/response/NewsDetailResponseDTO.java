package org.farmsystem.homepage.domain.news.dto.response;

import java.util.List;

public record NewsDetailResponseDTO(Long newsId, String title, String thumbnailUrl, String content, List<String> imageUrls) { }
