package org.farmsystem.homepage.domain.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationApi {

    @Operation(
            summary = "알림 구독 API",
            description = "실시간 알림을 수신하기 위해 SSE 연결을 생성하는 API입니다.",
            security = @SecurityRequirement(name = "token"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "SSE 연결이 성공적으로 생성되었습니다.",
                    content = @Content(
                            mediaType = "text/event-stream",
                            schema = @Schema(implementation = SseEmitter.class)
                    ))
    })
    SseEmitter subscribe(@AuthenticationPrincipal Long userId);
}