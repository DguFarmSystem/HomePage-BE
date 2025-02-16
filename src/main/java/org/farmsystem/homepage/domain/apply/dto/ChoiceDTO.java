package org.farmsystem.homepage.domain.apply.dto;

import lombok.Builder;

@Builder
public record ChoiceDTO(Long choiceId, String content) {
}
