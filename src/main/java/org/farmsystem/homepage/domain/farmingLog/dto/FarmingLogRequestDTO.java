package org.farmsystem.homepage.domain.farmingLog.dto;

import org.farmsystem.homepage.domain.farmingLog.entity.Category;

public record FarmingLogRequestDTO(
        String title,
        String content,
        Category category
) {}
