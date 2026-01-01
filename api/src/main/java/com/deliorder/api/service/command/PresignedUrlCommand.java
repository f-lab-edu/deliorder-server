package com.deliorder.api.service.command;

import com.deliorder.api.enums.ImageFileType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class PresignedUrlCommand {
    private final String fileName;
    private final ImageFileType fileType;
}
