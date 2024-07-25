package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SeatReservationLogErrorCode implements ErrorCode {

    SEAT_RESERVATION_NOT_AVAILABLE(HttpStatus.BAD_REQUEST.value(), "이미 매진 된 좌석입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;

}

