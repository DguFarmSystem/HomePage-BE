package org.farmsystem.homepage.domain.homepage.blog.dto.response;


import org.farmsystem.homepage.domain.homepage.blog.entity.BlogCategory;

import java.util.Set;

public record BlogResponseDTO(
        Long blogId,
        String link,
        Set<BlogCategory> categories,
        String approvalStatus
) {}
