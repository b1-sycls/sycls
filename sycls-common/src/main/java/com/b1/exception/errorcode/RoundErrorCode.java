package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RoundErrorCode implements ErrorCode {
    NOT_FOUND_ROUND(HttpStatus.NOT_FOUND.value(), "찾을 수 없는 공연입니다."),
    BOOKING_NOT_AVAILABLE(HttpStatus.BAD_REQUEST.value(), "예매 할 수 없는 공연입니다.");

    private final Integer httpStatusCode;
    private final String description;

}
