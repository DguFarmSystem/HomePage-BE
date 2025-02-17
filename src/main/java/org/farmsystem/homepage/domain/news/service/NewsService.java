package org.farmsystem.homepage.domain.news.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.news.dto.request.NewsRequestDTO;
import org.farmsystem.homepage.domain.news.entity.News;
import org.farmsystem.homepage.domain.news.repository.NewsRepository;
import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News getNewsById(Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NEWS_NOT_FOUND));
    }

    @Transactional
    public News createNews(NewsRequestDTO request) {
        News news = News.builder()
                .title(request.title())
                .content(request.content())
                .build();
        return newsRepository.save(news);
    }

    @Transactional
    public News updateNews(Long newsId, NewsRequestDTO request) {
        News news = getNewsById(newsId);
        news.updateTitleAndContent(request.title(), request.content());
        return news;
    }

    @Transactional
    public void deleteNews(Long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new BusinessException(ErrorCode.NEWS_NOT_FOUND);
        }
        newsRepository.deleteById(newsId);
    }
}
