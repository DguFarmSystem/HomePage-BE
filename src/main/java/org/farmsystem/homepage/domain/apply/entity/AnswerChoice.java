package org.farmsystem.homepage.domain.apply.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "answer_choice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerChoice {

    @EmbeddedId
    private AnswerChoiceId id;

    @MapsId("answerId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @MapsId("choiceId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice_id")
    private Choice choice;
}
