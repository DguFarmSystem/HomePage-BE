package org.farmsystem.homepage.domain.community.farmingLog.dto;

import org.farmsystem.homepage.domain.community.farmingLog.entity.Category;

public record FarmingLogRequestDTO(
        String title,
        String content,
        Category category
) {}
