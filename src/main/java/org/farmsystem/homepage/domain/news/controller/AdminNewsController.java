package org.farmsystem.homepage.domain.news.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.news.dto.request.NewsRequestDTO;
import org.farmsystem.homepage.domain.news.dto.response.NewsResponseDTO;
import org.farmsystem.homepage.domain.news.entity.News;
import org.farmsystem.homepage.domain.news.service.NewsService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/news")
@RequiredArgsConstructor
public class AdminNewsController implements AdminNewsApi{

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createNews(@RequestBody @Valid NewsRequestDTO request) {
        News news = newsService.createNews(request);
        NewsResponseDTO responseDto = new NewsResponseDTO(news.getNewsId(), news.getTitle(), news.getContent());
        return SuccessResponse.created(responseDto);
    }

    @PutMapping("/{newsId}")
    public ResponseEntity<SuccessResponse<?>> updateNews(
            @PathVariable("newsId") Long newsId,
            @RequestBody @Valid NewsRequestDTO request) {
        News updatedNews = newsService.updateNews(newsId, request);
        NewsResponseDTO responseDto = new NewsResponseDTO(updatedNews.getNewsId(), updatedNews.getTitle(), updatedNews.getContent());
        return SuccessResponse.ok(responseDto);
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<Void> deleteNews(@PathVariable("newsId") Long newsId) {
        newsService.deleteNews(newsId);
        return ResponseEntity.noContent().build();
    }
}
