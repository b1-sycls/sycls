package com.b1.S3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum S3Type {

    CONTENT_MAIN_IMAGE("main"),
    CONTENT_DETAIL_IMAGE("detail"),
    ;

    private final String value;
}
