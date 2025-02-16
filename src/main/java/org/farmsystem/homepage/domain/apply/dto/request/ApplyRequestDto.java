package org.farmsystem.homepage.domain.apply.dto.request;

import org.farmsystem.homepage.domain.apply.dto.AnswerDto;

import java.util.List;

public record ApplyRequestDto(Long applyId, List<AnswerDto> answers) {
    // TODO: Validation 추가
}
