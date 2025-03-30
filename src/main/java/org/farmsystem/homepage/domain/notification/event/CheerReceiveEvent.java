package org.farmsystem.homepage.domain.notification.event;

import org.farmsystem.homepage.domain.common.entity.Track;

public record CheerReceiveEvent(
        Long cheeredId,
        Integer CheererGeneration,
        Track CheererTrack,
        String CheererName,
        Long cheerId
) {
}
