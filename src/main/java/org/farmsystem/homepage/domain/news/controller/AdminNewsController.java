package org.farmsystem.homepage.domain.news.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.news.dto.request.NewsRequestDTO;
import org.farmsystem.homepage.domain.news.dto.response.NewsResponseDTO;
import org.farmsystem.homepage.domain.news.entity.News;
import org.farmsystem.homepage.domain.news.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/news")
@RequiredArgsConstructor
public class AdminNewsController {

    private final NewsService newsService;

    // 소식 작성
    @PostMapping
    public ResponseEntity<NewsResponseDTO> createNews(@RequestBody @Valid NewsRequestDTO request) {
        News news = newsService.createNews(request);
        NewsResponseDTO responseDto = new NewsResponseDTO(
                news.getNewsId(),
                news.getTitle(),
                news.getContent()
        );
        return ResponseEntity.ok(responseDto);
    }

    // 소식 수정
    @PutMapping("/{newsId}")
    public ResponseEntity<NewsResponseDTO> updateNews(
            @PathVariable("newsId") Long newsId, // 명시적으로 변수명 지정
            @RequestBody @Valid NewsRequestDTO request
    ) {
        News updatedNews = newsService.updateNews(newsId, request);
        NewsResponseDTO responseDto = new NewsResponseDTO(
                updatedNews.getNewsId(),
                updatedNews.getTitle(),
                updatedNews.getContent()
        );
        return ResponseEntity.ok(responseDto);
    }

    // 소식 삭제
    @DeleteMapping("/{newsId}")
    public ResponseEntity<Void> deleteNews(@PathVariable("newsId") Long newsId) { // 명확한 매핑
        newsService.deleteNews(newsId);
        return ResponseEntity.noContent().build();
    }
}
