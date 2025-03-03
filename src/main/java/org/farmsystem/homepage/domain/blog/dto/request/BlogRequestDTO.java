package org.farmsystem.homepage.domain.blog.dto.request;

import org.farmsystem.homepage.domain.blog.entity.BlogCategory;

import java.util.Set;

public record BlogRequestDTO(
        String link,
        Set<BlogCategory> categories
) {}
