package com.b1.s3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum S3UrlPathType {

    CONTENT_MAIN_IMAGE_PATH("content/main"),
    CONTENT_DETAIL_IMAGE_PATH("content/detail"),
    CONTENT_CAST_IMAGE_PATH("cast"),
    ;

    private final String value;
}
