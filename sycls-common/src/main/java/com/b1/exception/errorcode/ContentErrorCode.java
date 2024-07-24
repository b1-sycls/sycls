package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ContentErrorCode implements ErrorCode {

    CONTENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "공연 정보를 찾지 못하였습니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
