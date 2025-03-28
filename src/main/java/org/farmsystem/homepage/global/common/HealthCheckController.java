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

    // 서버 상태 확인 API
    @GetMapping("/")
    public ResponseEntity<SuccessResponse<?>> FarmSysytemServer() {
        return SuccessResponse.ok("Hello! FarmSysytem Server!, Blue -> Green");
    }

}
