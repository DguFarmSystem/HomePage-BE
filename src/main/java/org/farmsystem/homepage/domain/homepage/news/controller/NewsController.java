package org.farmsystem.homepage.domain.homepage.news.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.homepage.news.dto.response.NewsListResponseDTO;
import org.farmsystem.homepage.domain.homepage.news.dto.response.NewsDetailResponseDTO;
import org.farmsystem.homepage.domain.homepage.news.service.NewsService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController implements NewsApi{

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getAllNews() {
        List<NewsListResponseDTO> dtoList = newsService.getAllNews();
        return SuccessResponse.ok(dtoList);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<SuccessResponse<?>> getNewsById(@PathVariable("newsId") Long newsId) {
        NewsDetailResponseDTO responseDto = newsService.getNewsById(newsId);
        return SuccessResponse.ok(responseDto);
    }
}