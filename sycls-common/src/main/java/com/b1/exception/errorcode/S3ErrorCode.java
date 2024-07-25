package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum S3ErrorCode implements ErrorCode {

    S3_INVALID_IMAGE_TYPE(HttpStatus.BAD_REQUEST.value(), "이미지 타입이 맞지 않습니다."),
    S3_MISSING_IMAGE(HttpStatus.NOT_FOUND.value(), "이미지가 누락되었습니다."),
    S3_UPLOADING_FAIL(HttpStatus.BAD_REQUEST.value(), "이미지 업로드 중 오류가 발생하였습니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
