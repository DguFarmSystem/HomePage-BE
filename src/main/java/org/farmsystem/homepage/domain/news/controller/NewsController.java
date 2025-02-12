package org.farmsystem.homepage.domain.news.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.news.dto.response.NewsResponseDTO;
import org.farmsystem.homepage.domain.news.entity.News;
import org.farmsystem.homepage.domain.news.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    // 모든 소식 조회
    @GetMapping
    public ResponseEntity<List<NewsResponseDTO>> getAllNews() {
        List<News> newsList = newsService.getAllNews();
        List<NewsResponseDTO> dtoList = newsList.stream()
                .map(news -> new NewsResponseDTO(
                        news.getNewsId(),
                        news.getTitle(),
                        news.getContent()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // 소식 단건 조회
    @GetMapping("/{newsId}")
    public ResponseEntity<NewsResponseDTO> getNewsById(@PathVariable("newsId") Long newsId) {
        News news = newsService.getNewsById(newsId);
        NewsResponseDTO dto = new NewsResponseDTO(
                news.getNewsId(),
                news.getTitle(),
                news.getContent());
        return ResponseEntity.ok(dto);
    }
}
