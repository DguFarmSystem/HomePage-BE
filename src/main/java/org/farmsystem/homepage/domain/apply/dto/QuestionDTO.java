package org.farmsystem.homepage.domain.apply.dto;

import lombok.Builder;
import org.farmsystem.homepage.domain.apply.entity.QuestionType;
import org.farmsystem.homepage.domain.common.entity.Track;

import java.util.List;

@Builder
public record QuestionDTO(Long questionId, Track track, Boolean isRequired, String content, Integer maxLength,
                          QuestionType type, Boolean isDuplicated, Integer priority, List<ChoiceDTO> choices) {
}
