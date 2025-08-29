package org.farmsystem.homepage.domain.community.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeedEventType {
    ATTENDANCE(1),   // 출석 1개
    CHEER(2),        // 응원 2개
    FARMING_LOG(5);  // 파밍로그 5개

    private final int seedAmount;
}
