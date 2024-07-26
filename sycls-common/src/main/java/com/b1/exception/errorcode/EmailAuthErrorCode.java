package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EmailAuthErrorCode implements ErrorCode {

    USER_CODE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 사용자의 코드를 찾을 수 없습니다."),
    CODE_MISMATCH(HttpStatus.BAD_REQUEST.value(), "코드가 일치하지 않습니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
