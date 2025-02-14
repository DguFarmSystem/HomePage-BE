package org.farmsystem.homepage.domain.news.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.farmsystem.homepage.global.common.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "news")
@NoArgsConstructor
public class News extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
