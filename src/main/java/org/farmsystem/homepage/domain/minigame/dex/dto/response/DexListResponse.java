package org.farmsystem.homepage.domain.minigame.dex.dto.response;

import java.util.List;

public record DexListResponse(
        List<DexResponse> dexList
) {
}