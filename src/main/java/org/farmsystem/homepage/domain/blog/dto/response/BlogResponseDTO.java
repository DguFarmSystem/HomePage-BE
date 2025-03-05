package org.farmsystem.homepage.domain.blog.dto.response;


import org.farmsystem.homepage.domain.blog.entity.BlogCategory;

import java.util.Set;

public record BlogResponseDTO(
        Long blogId,
        String link,
        Set<BlogCategory> categories,
        String approvalStatus
) {}
