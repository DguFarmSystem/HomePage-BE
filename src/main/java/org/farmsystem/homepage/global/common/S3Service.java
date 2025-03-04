package org.farmsystem.homepage.global.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file, String directory) throws IOException {
        String fileName = UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());
        return uploadFileToS3(file, directory, fileName);
    }

    // 사용자 아이디 기반 프로필 이미지 파일 업로드
    public String uploadFileByUserId(MultipartFile file, Long userId) {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!isValidProfilImageExtension(fileExtension)) {throw new InvalidValueException(INVALID_IMAGE_FORMAT);}
        String fileName = "profile_user" + userId + fileExtension;
        return uploadFileToS3(file, profileDirectory, fileName);
    }

    // 파일 확장자 추출
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        return (lastDotIndex > 0) ? fileName.substring(lastDotIndex).toLowerCase() : "";
    }

    // 프로필 이미지 확장자 유효성 검사
    private boolean isValidProfilImageExtension(String extension) {
        return extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png");
    }

    // S3에 파일 업로드
    private String uploadFileToS3(MultipartFile file, String directory, String fileName) {
        String filePath = directory + "/" + fileName;

        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream, metadata);

            amazonS3.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new IOException("S3 파일 업로드 중 오류 발생: " + e.getMessage(), e);
        }

        return amazonS3.getUrl(bucketName, filePath).toString();
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        return (lastDotIndex > 0) ? fileName.substring(lastDotIndex).toLowerCase() : "";
    }
}
