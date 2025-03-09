package org.farmsystem.homepage.domain.common.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.farmsystem.homepage.domain.common.dto.request.PresignedUrlRequestDTO;
import org.farmsystem.homepage.domain.common.dto.response.PresignedUrlResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${s3.profile-directory}")
    private String profileDirectory;

    // 프로필 사진 업로드용 presigned url 생성
    public PresignedUrlResponseDTO generateProfilePresignedUrl(Long userId) {

        String directory = profileDirectory;
        String fileName = "profile_user" + userId;

        PresignedUrlRequestDTO presignedUrlRequest = new PresignedUrlRequestDTO(directory, fileName);
        return generatePresignedUrl(presignedUrlRequest);
    }

    // presigned url 생성
    public PresignedUrlResponseDTO generatePresignedUrl(PresignedUrlRequestDTO presignedUrlRequest) {
        String filePath = presignedUrlRequest.directory() + "/" + presignedUrlRequest.fileName();
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 15);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, filePath)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return PresignedUrlResponseDTO.of(url.toString());
    }

    // 파일 확장자 추출
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    // 이미지 확장자 유효성 검증
    private boolean isValidImageExtension(String extension) {
        String normalizedExtension = extension.toLowerCase(); // 대소문자 구분 방지
        return Arrays.asList(".jpg", ".jpeg", ".png").contains(normalizedExtension);
    }
}
