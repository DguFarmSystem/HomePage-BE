package org.farmsystem.homepage.domain.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.farmsystem.homepage.domain.blog.entity.BlogCategory;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

public record BlogRequestDTO(
        @NotBlank(message = "링크는 필수 입력 항목입니다.")
        @URL(message = "유효한 URL 형식이 아닙니다.")
        String link,
        @NotEmpty(message = "카테고리는 최소 하나 이상 선택해야 합니다.")
        Set<BlogCategory> categories
) {}
