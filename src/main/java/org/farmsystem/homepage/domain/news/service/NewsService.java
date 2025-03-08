package org.farmsystem.homepage.domain.news.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.news.dto.request.NewsRequestDTO;
import org.farmsystem.homepage.domain.news.dto.response.NewsDetailResponseDTO;
import org.farmsystem.homepage.domain.news.dto.response.NewsListResponseDTO;
import org.farmsystem.homepage.domain.news.entity.News;
import org.farmsystem.homepage.domain.news.repository.NewsRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public List<NewsListResponseDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(news -> new NewsListResponseDTO(news.getNewsId(), news.getTitle(), news.getThumbnailUrl()))
                .collect(Collectors.toList());
    }

    public NewsDetailResponseDTO getNewsById(Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NEWS_NOT_FOUND));
        return new NewsDetailResponseDTO(news.getNewsId(), news.getTitle(), news.getThumbnailUrl(), news.getContent(), news.getImageUrls());
    }

    @Transactional
    public NewsDetailResponseDTO createNews(NewsRequestDTO request) {
        News news = News.builder()
                .title(request.title())
                .content(request.content())
                .thumbnailUrl(request.thumbnailUrl())
                .imageUrls(request.imageUrls())
                .build();
        newsRepository.save(news);
        return new NewsDetailResponseDTO(news.getNewsId(), news.getTitle(), news.getThumbnailUrl(), news.getContent(), news.getImageUrls());
    }

    @Transactional
    public NewsDetailResponseDTO updateNews(Long newsId, NewsRequestDTO request) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NEWS_NOT_FOUND));
        news.updateNews(request.title(), request.content(), request.thumbnailUrl(), request.imageUrls());
        return new NewsDetailResponseDTO(news.getNewsId(), news.getTitle(), news.getThumbnailUrl(), news.getContent(), news.getImageUrls());
    }

    @Transactional
    public void deleteNews(Long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new BusinessException(ErrorCode.NEWS_NOT_FOUND);
        }
        newsRepository.deleteById(newsId);
    }
}