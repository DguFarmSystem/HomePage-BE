package org.farmsystem.homepage.domain.apply.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AnswerDto(Long questionId, String content, List<Long> choiceId) {
    // TODO: Validation 추가
}
