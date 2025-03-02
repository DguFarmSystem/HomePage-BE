package org.farmsystem.homepage.domain.apply.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.farmsystem.homepage.domain.common.entity.Track;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Track track;

    @Column(nullable = false)
    private Boolean isRequired;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private Integer maxLength;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType type;

    private Boolean isDuplicated;

    @Column(nullable = false)
    private int priority;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    @OrderBy("priority ASC")
    private List<Choice> choices =  new ArrayList<>();
}
