package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SeatReservationLogErrorCode implements ErrorCode {

    SEAT_RESERVATION_NOT_AVAILABLE(HttpStatus.BAD_REQUEST.value(), "이미 예매 된 좌석입니다."),
    SEAT_RESERVATION_ALREADY_DISABLE(HttpStatus.BAD_REQUEST.value(), "이미 취소 된 예매 정보입니다."),
    SEAT_RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "찾을 수 없는 예매 정보입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;

}

