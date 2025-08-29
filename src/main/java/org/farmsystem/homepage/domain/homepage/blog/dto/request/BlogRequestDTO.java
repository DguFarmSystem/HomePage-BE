package org.farmsystem.homepage.domain.homepage.blog.dto.request;

import org.farmsystem.homepage.domain.homepage.blog.entity.BlogCategory;

import java.util.Set;

public record BlogRequestDTO(
        String link,
        Set<BlogCategory> categories
) {}
