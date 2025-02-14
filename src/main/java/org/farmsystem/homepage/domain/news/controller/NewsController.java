package org.farmsystem.homepage.domain.news.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.news.dto.response.NewsResponseDTO;
import org.farmsystem.homepage.domain.news.entity.News;
import org.farmsystem.homepage.domain.news.service.NewsService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getAllNews() {
        List<News> newsList = newsService.getAllNews();
        List<NewsResponseDTO> dtoList = newsList.stream()
                .map(news -> new NewsResponseDTO(
                        news.getNewsId(),
                        news.getTitle(),
                        news.getContent()))
                .collect(Collectors.toList());
        return SuccessResponse.ok(dtoList);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<SuccessResponse<?>> getNewsById(@PathVariable("newsId") Long newsId) {
        News news = newsService.getNewsById(newsId);
        NewsResponseDTO responseDto = new NewsResponseDTO(news.getNewsId(), news.getTitle(), news.getContent());
        return SuccessResponse.ok(responseDto);
    }
}
