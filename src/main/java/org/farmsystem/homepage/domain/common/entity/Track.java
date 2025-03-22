package org.farmsystem.homepage.domain.common.entity;

import java.util.Arrays;

public enum Track {
    UNION, GAMING_VIDEO, SECURITY_WEB, AI, IOT_ROBOTICS, BIGDATA;

    public static Track from(String value) {
        return Arrays.stream(values())
                .filter(t -> t.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 트랙: " + value));
    }
}
