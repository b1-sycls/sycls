package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {

    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "토큰을 찾을 수 없습니다."),
    USER_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 사용자의 토큰을 찾을 수 없습니다."),
    IS_BLACKLIST_TOKEN(HttpStatus.BAD_REQUEST.value(), "블랙리스트 토큰입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
