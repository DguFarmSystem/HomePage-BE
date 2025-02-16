package org.farmsystem.homepage.global.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
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
        String uniqueFileName = UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());
        String filePath = uniqueFileName;

        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream, metadata);

            amazonS3.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new IOException("파일 업로드 중 오류 발생: " + e.getMessage(), e);
        }

        return amazonS3.getUrl(bucketName, filePath).toString();
    }


    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        return (lastDotIndex > 0) ? fileName.substring(lastDotIndex) : "";
    }
}
