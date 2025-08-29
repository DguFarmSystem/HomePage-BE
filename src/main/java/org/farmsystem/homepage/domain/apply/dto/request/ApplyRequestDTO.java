package org.farmsystem.homepage.domain.apply.dto.request;

import jakarta.validation.constraints.NotNull;
import org.farmsystem.homepage.domain.apply.dto.AnswerDTO;
import org.farmsystem.homepage.domain.common.entity.Track;

import java.util.List;

public record ApplyRequestDTO(
        @NotNull
        Long applyId,
        String name,
        String major,
        String phoneNumber,
        String email,
        @NotNull
        Track track,
        List<AnswerDTO> answers
) {
}
