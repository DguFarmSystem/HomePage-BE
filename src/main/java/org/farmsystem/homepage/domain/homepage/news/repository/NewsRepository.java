package org.farmsystem.homepage.domain.homepage.news.repository;

import org.farmsystem.homepage.domain.homepage.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
