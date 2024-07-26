package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PageErrorCode implements ErrorCode {

    INVALID_PAGE_NUMBER(HttpStatus.BAD_REQUEST.value(), "페이지 번호가 0 이하입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
