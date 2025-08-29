package org.farmsystem.homepage.domain.homepage.apply.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerChoiceId {

    private Long answerId;

    private Long choiceId;

    @Builder
    public AnswerChoiceId(Long answerId, Long choiceId) {
        this.answerId = answerId;
        this.choiceId = choiceId;
    }
}
