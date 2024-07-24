package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RoundErrorCode implements ErrorCode {

    CONFLICTING_RESERVATION(HttpStatus.BAD_REQUEST.value(), "해당시간에 예약이 이미 되어있습니다."),
    INVALID_DATE(HttpStatus.BAD_REQUEST.value(), "공연일이 과거일 수 없습니다."),
    INVALID_TIME(HttpStatus.BAD_REQUEST.value(), "공연 종료시간이 공연 시작시간보다 빠를 수 없습니다."),
    ROUND_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "회차를 찾을 수 없습니다."),
    STATUS_EQUALS(HttpStatus.BAD_REQUEST.value(), "회차의 상태가 동일합니다"),
    STATUS_ALREADY_CLOSED(HttpStatus.BAD_REQUEST.value(), "이미 끝난 회차 입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
