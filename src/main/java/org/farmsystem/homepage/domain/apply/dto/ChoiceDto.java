package org.farmsystem.homepage.domain.apply.dto;

import lombok.Builder;

@Builder
public record ChoiceDto(Long choiceId, String content) {
}
