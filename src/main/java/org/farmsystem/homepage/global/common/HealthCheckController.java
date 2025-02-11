package org.farmsystem.homepage.global.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.farmsystem.homepage.global.config.auth.jwt.JwtProvider;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class HealthCheckController implements HealthCheckApi {
    private final JwtProvider jwtProvider;

    // 서버 상태 확인 API
    @GetMapping("/")
    public ResponseEntity<SuccessResponse<?>> FarmSysytemServer() {
        return SuccessResponse.ok("Hello! FarmSysytem Server!");
    }

    // 임시 토큰 발급 API. 추후 로그인 기능이 완성되면 삭제할 예정
    @PostMapping("/token/{userId}")
    public ResponseEntity<SuccessResponse<?>> getToken(@PathVariable Long userId) {
        String accessToken = jwtProvider.getIssueToken(userId, true);
        String refreshToken = jwtProvider.getIssueToken(userId, false);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return SuccessResponse.created(tokens);
    }
}