package org.farmsystem.homepage.domain.homepage.apply.dto;

import lombok.Builder;
import org.farmsystem.homepage.domain.homepage.apply.entity.Question;
import org.farmsystem.homepage.domain.homepage.apply.entity.QuestionType;
import org.farmsystem.homepage.domain.common.entity.Track;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record QuestionDTO(
        Long questionId,
        Track track,
        Boolean isRequired,
        String content,
        Integer maxLength,
        QuestionType type,
        Boolean isDuplicated,
        Integer priority,
        List<ChoiceDTO> choices
) {
    public static QuestionDTO from(Question question) {
        return QuestionDTO.builder()
                .questionId(question.getQuestionId())
                .track(question.getTrack())
                .isRequired(question.getIsRequired())
                .content(question.getContent())
                .maxLength(question.getMaxLength())
                .type(question.getType())
                .isDuplicated(question.getIsDuplicated())
                .priority(question.getPriority())
                .choices(question.getChoices().stream()
                        .map(choice -> ChoiceDTO.builder()
                                .choiceId(choice.getChoiceId())
                                .content(choice.getContent())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
