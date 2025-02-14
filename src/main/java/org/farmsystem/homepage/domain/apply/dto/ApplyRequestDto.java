package org.farmsystem.homepage.domain.apply.dto;

import java.util.List;

public record ApplyRequestDto(Long applyId, List<AnswerDto> answers) {
    // TODO: Validation 추가
}
