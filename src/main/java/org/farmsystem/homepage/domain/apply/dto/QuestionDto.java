package org.farmsystem.homepage.domain.apply.dto;

import lombok.Builder;
import org.farmsystem.homepage.domain.apply.entity.ApplyTrack;
import org.farmsystem.homepage.domain.apply.entity.QuestionType;

import java.util.List;

@Builder
public record QuestionDto(Long questionId, ApplyTrack track, Boolean isRequired, String content, Integer maxLength,
                          QuestionType type, Boolean isDuplicated, Integer priority, List<ChoiceDto> choices) {
}
