package org.farmsystem.homepage.domain.apply.dto.response;

import lombok.Builder;
import org.farmsystem.homepage.domain.apply.dto.AnswerDTO;
import org.farmsystem.homepage.domain.apply.entity.ApplyStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record LoadApplyResponseDTO(Long applyId, ApplyStatusEnum status, LocalDateTime updatedAt, List<AnswerDTO> answers) {
}
