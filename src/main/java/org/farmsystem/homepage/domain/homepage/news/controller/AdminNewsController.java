package org.farmsystem.homepage.domain.homepage.news.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.homepage.news.dto.request.NewsRequestDTO;
import org.farmsystem.homepage.domain.homepage.news.dto.response.NewsDetailResponseDTO;
import org.farmsystem.homepage.domain.homepage.news.service.NewsService;
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
        NewsDetailResponseDTO responseDto = newsService.createNews(request);
        return SuccessResponse.created(responseDto);
    }

    @PutMapping("/{newsId}")
    public ResponseEntity<SuccessResponse<?>> updateNews(
            @PathVariable("newsId") Long newsId,
            @RequestBody @Valid NewsRequestDTO request) {
        NewsDetailResponseDTO responseDto = newsService.updateNews(newsId, request);
        return SuccessResponse.ok(responseDto);
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<SuccessResponse<?>> deleteNews(@PathVariable("newsId") Long newsId) {
        newsService.deleteNews(newsId);
        return SuccessResponse.noContent();
    }
}
