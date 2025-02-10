package org.farmsystem.homepage.global.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Common API", description = "공통 API")
public interface HealthCheckApi {

    @Operation(
            summary = "서버 상태 확인 API",
            description = "FarmSysytem 서버 실행 상태를 확인하는 API입니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "서버가 정상적으로 작동 중"),
            @ApiResponse(responseCode = "404", description = "서버를 찾을 수 없음")
    })
    ResponseEntity<SuccessResponse<?>> FarmSysytemServer();

    @Operation(
            summary = "임시 토큰 발급 API",
            description = "UserId로 임시 토큰을 발급받는 API입니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "토큰 발급 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    ResponseEntity<SuccessResponse<?>> getToken(
            @Parameter(description = "userId, 임의 입력값", required = true, example = "1")
            Long userId
    );
}
