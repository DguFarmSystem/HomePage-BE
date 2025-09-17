package org.farmsystem.homepage.domain.minigame.farm.entity;

import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;

public enum PlantStatus {
    EMPTY,
    GROWING,
    GROWN;

    public static PlantStatus fromString(String status) {
        try {
            return PlantStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}
