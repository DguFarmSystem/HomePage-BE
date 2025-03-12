package org.farmsystem.homepage.domain.apply.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoadApplyRequestDTO(
        @NotBlank
        @Size(min = 10, max = 10)
        String studentNumber,

        @NotBlank
        @Size(min = 6, max = 6)
        String password
) {
}
