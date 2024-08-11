package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {

    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "토큰을 찾을 수 없습니다."),
    IS_BLACKLIST_TOKEN(HttpStatus.BAD_REQUEST.value(), "블랙리스트 토큰입니다."),
    INVALID_SIGNATURE(HttpStatus.FORBIDDEN.value(), "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "Expired JWT token, 만료된 JWT token 입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.FORBIDDEN.value(), "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다."),
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST.value(), "JWT claims is empty, 잘못된 JWT 토큰 입니다.");

    private final Integer httpStatusCode;
    private final String description;
}
