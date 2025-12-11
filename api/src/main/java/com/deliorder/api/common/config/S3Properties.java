package com.deliorder.api.common.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("spring.cloud.aws")
public class S3Properties {

    private final String region;
    private final Credentials credentials;
    private final S3 s3;

    @Getter
    @RequiredArgsConstructor
    public static class Credentials {
        private final String accessKey;
        private final String secretKey;
    }

    @Getter
    @RequiredArgsConstructor
    public static class S3 {
        private final String bucket;
        private final Long presignedUrlExpiration;
    }
}
