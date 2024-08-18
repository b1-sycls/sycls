package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name()),
    ADD_LOCK_ERROR(HttpStatus.BAD_REQUEST.value(), "등록이 진행 중 입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;

}
