package org.farmsystem.homepage.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    // S3 호환 스토리지(Railway Bucket 등) 엔드포인트, 비어 있으면 AWS S3 사용
    @Value("${cloud.aws.s3.endpoint:}")
    private String endpoint;

    @Bean
    public S3Client s3Client() {

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        S3ClientBuilder builder = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials));
        if (!endpoint.isBlank()) builder.endpointOverride(URI.create(endpoint));
        return builder.build();
    }

    @Bean
    public S3Presigner s3Presigner() {

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(accessKey, secretKey);

        S3Presigner.Builder builder = S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(region));
        if (!endpoint.isBlank()) builder.endpointOverride(URI.create(endpoint));
        return builder.build();
    }
}
