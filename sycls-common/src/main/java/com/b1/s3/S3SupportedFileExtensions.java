package com.b1.s3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum S3SupportedFileExtensions {

    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    ;

    private final String extension;
}
