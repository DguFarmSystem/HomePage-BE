package org.farmsystem.homepage.domain.cheer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.farmsystem.homepage.domain.cheer.entity.CheerTag;

public record CheerRequestDTO(
        @NotNull
        Long cheererId,
        @NotNull
        Long cheeredId,
        @NotNull
        CheerTag tag,
        @NotBlank
        String content
) {
}
