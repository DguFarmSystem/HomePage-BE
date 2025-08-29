package org.farmsystem.homepage.domain.homepage.apply.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id")
    private Apply apply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    public Answer(String content, Apply apply, Question question) {
        this.content = content;
        this.apply = apply;
        this.question = question;
    }

    @OneToMany(mappedBy = "answer")
    @BatchSize(size = 5)
    private List<AnswerChoice> answerChoices = new ArrayList<>();

    public void updateContent(String content) {
        this.content = content;
    }
}
