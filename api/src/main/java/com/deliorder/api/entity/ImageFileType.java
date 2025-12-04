package com.deliorder.api.entity;

import com.deliorder.api.common.exception.ErrorCode;
import com.deliorder.api.common.exception.HandledException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ImageFileType {
    JPEG("image/jpeg"),
    PNG("image/png"),
    WEBP("image/webp");

    private final String mimeType;

    public static ImageFileType of(String mimeType) {
        return Arrays.stream(values())
                .filter(f -> f.mimeType.equalsIgnoreCase(mimeType))
                .findFirst()
                .orElseThrow(() -> new HandledException(ErrorCode.INVALID_FILE_TYPE, "지원하지 않는 파일 형식입니다: " + mimeType));
    }
}
