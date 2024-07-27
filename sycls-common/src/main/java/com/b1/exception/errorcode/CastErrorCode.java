package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CastErrorCode implements ErrorCode {

    CAST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "출연진을 찾을 수 없습니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
