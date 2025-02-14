package org.farmsystem.homepage.domain.apply.dto;

import lombok.Builder;
import org.farmsystem.homepage.domain.apply.entity.ApplyStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record LoadApplyResponseDto(Long applyId, ApplyStatus status, LocalDateTime updatedAt, List<AnswerDto> answers) {
}
