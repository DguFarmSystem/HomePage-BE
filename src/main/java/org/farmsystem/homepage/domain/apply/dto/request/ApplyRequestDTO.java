package org.farmsystem.homepage.domain.apply.dto.request;

import org.farmsystem.homepage.domain.apply.dto.AnswerDTO;

import java.util.List;

public record ApplyRequestDTO(Long applyId, List<AnswerDTO> answers) {
    // TODO: Validation 추가
}
