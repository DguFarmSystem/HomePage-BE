package org.farmsystem.homepage.domain.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.farmsystem.homepage.domain.notification.dto.NotificationResponseDto;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Operation(
            summary = "알림 목록 조회 API",
            description = "사용자의 알림 목록을 조회합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 읽음 처리 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NotificationResponseDto.class))
                    )
            ),
            @ApiResponse(responseCode = "403", description = "알림에 대한 권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "알림을 찾을 수 없음", content = @Content)
    })
    ResponseEntity<SuccessResponse<?>> getNotifications(@AuthenticationPrincipal Long userId);

    @Operation(
            summary = "알림 읽음 처리 API",
            description = "사용자가 특정 알림을 읽은 후, 해당 알림의 isRead 필드를 true로 업데이트합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 읽음 처리 성공"),
            @ApiResponse(responseCode = "403", description = "알림에 대한 권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "알림을 찾을 수 없음", content = @Content)
    })
    ResponseEntity<SuccessResponse<?>> readNotifications(@PathVariable Long notificationId, @AuthenticationPrincipal Long userId);
}