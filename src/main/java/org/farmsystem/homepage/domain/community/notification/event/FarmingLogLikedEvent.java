package org.farmsystem.homepage.domain.community.notification.event;

import org.farmsystem.homepage.domain.common.entity.Track;

public record FarmingLogLikedEvent(
        Long receiverId,
        Integer likerGeneration,
        Track likerTrack,
        String likerName,
        String farmingLogTitle,
        Long farmingLogId
) {
}
