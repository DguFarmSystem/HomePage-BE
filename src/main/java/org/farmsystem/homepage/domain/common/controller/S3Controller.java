package org.farmsystem.homepage.domain.common.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.common.dto.request.PresignedUrlRequestDTO;
import org.farmsystem.homepage.domain.common.dto.response.PresignedUrlResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.farmsystem.homepage.domain.common.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
