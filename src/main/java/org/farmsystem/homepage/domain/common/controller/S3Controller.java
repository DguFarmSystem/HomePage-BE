package org.farmsystem.homepage.domain.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.common.dto.request.PresignedUrlRequestDTO;
import org.farmsystem.homepage.domain.common.dto.response.PresignedUrlResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.farmsystem.homepage.domain.common.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RequestMapping("/api/s3")
@RestController
public class S3Controller implements S3Api{

    private final S3Service s3Service;

    // S3 presigned URL 생성 API
    @PostMapping("presigned-url")
    public ResponseEntity<SuccessResponse<?>> generatePresignedUrl(@RequestBody PresignedUrlRequestDTO presignedUrlRequest) {
        PresignedUrlResponseDTO presignedUrl = s3Service.generatePresignedUrl(presignedUrlRequest);
        return SuccessResponse.ok(presignedUrl);
    }

    // 프로필 사진 업로드용 presigned URL 생성 API
    @PostMapping("presigned-url/profile")
    public ResponseEntity<SuccessResponse<?>> generateProfilePresignedUrl(@AuthenticationPrincipal Long userId) {
        PresignedUrlResponseDTO presignedUrl = s3Service.generateProfilePresignedUrl(userId);
        return SuccessResponse.ok(presignedUrl);
    }

    // 파일 조회 API (private 버킷이라 presigned URL로 redirect)
    @GetMapping("files/**")
    public ResponseEntity<Void> getFile(HttpServletRequest request) {
        String filePath = UriUtils.decode(request.getRequestURI().substring("/api/s3/files/".length()), StandardCharsets.UTF_8);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(s3Service.generateGetPresignedUrl(filePath)))
                .build();
    }
}
