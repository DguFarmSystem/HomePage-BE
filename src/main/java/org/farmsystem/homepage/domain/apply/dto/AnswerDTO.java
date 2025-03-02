package org.farmsystem.homepage.domain.apply.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record AnswerDTO(
        @NotNull
        Long questionId,
        String content,
        List<Long> choiceId
) {
}