package org.farmsystem.homepage.domain.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Track {
    UNION("유니온"),
    GAMING_VIDEO("게임/영상"),
    SECURITY_WEB("보안/웹"),
    AI("인공지능"),
    IOT_ROBOTICS("사물인터넷/로봇"),
    BIGDATA("빅데이터");

    private final String koreanName;
    public static Track from(String value) {
        return Arrays.stream(values())
                .filter(t -> t.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 트랙: " + value));
    }

    public static Track fromKoreanName(String koreanName) {
        return Arrays.stream(values())
                .filter(t -> t.koreanName.equalsIgnoreCase(koreanName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 트랙: " + koreanName));
    }
}


