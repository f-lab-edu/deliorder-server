package com.deliorder.api.service;

import com.deliorder.api.api.dto.PresignedUrlRequest;
import com.deliorder.api.api.dto.PresignedUrlResponse;
import com.deliorder.api.common.config.S3Properties;
import com.deliorder.api.common.exception.ErrorCode;
import com.deliorder.api.common.exception.HandledException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final S3Presigner s3Presigner;
    private final S3Properties s3Properties;

    private static final String S3_URL_PREFIX = "https://";
    private static final String S3_URL_SUFFIX = ".s3.amazonaws.com/";

    public PresignedUrlResponse createPresignedGetUrl(Long menuId, PresignedUrlRequest request) {
        try {
            String bucketName = s3Properties.getS3().getBucket();
            Long presignedUrlExpiration = s3Properties.getS3().getPresignedUrlExpiration();

            String fileExtension = extractExtension(request.getFileName());
            String key = generateKey(menuId, fileExtension);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(request.getFileType().getMimeType())
                    .build();

            PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(
                    PutObjectPresignRequest.builder()
                            .signatureDuration(Duration.ofMinutes(presignedUrlExpiration))
                            .putObjectRequest(putObjectRequest)
                            .build()
            );

            String presignedUrl = presignedRequest.url().toString();
            String fileUrl = getFileUrl(bucketName, key);

            return PresignedUrlResponse.builder()
                    .presignedUrl(presignedUrl)
                    .fileUrl(fileUrl)
                    .build();

        } catch (S3Exception | SdkClientException | IllegalArgumentException e) {
            log.error("[S3] Presigned URL 생성 중 예외 발생", e);
            throw new HandledException(ErrorCode.S3_PRESIGNED_URL_CREATION_FAILED);
        }
    }

    private static String generateKey(Long menuId, String fileExtension) {
        return "menu" + menuId + "/" + UUID.randomUUID() + fileExtension;
    }

    private String getFileUrl(String bucketName, String key) {
        return S3_URL_PREFIX + bucketName + S3_URL_SUFFIX + key;
    }

    private String extractExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.')); // 예: ".png"
    }
}
