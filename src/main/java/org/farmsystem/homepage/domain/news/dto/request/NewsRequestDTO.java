package org.farmsystem.homepage.domain.news.dto.request;

import java.util.List;

public record NewsRequestDTO(String title, String content, String thumbnailUrl, List<String> imageUrls) { }
