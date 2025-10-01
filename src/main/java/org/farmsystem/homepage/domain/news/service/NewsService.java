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
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepository;

    public List<NewsListResponseDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(NewsListResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public NewsDetailResponseDTO getNewsById(Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NEWS_NOT_FOUND));
        return NewsDetailResponseDTO.fromEntity(news);
    }

    @Transactional
    public NewsDetailResponseDTO createNews(NewsRequestDTO request) {
        News news = new News(
                request.title(),
                request.content(),
                request.thumbnailUrl(),
                request.imageUrls(),
                request.tags()
        );
        newsRepository.save(news);
        return NewsDetailResponseDTO.fromEntity(news);
    }

    @Transactional
    public NewsDetailResponseDTO updateNews(Long newsId, NewsRequestDTO request) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NEWS_NOT_FOUND));
        news.updateNews(
                request.title(),
                request.content(),
                request.thumbnailUrl(),
                request.imageUrls(),
                request.tags()
        );
        return NewsDetailResponseDTO.fromEntity(news);
    }


    @Transactional
    public void deleteNews(Long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new BusinessException(ErrorCode.NEWS_NOT_FOUND);
        }
        newsRepository.deleteById(newsId);
    }
}