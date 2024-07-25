package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SeatGradeErrorCode implements ErrorCode {

    NOT_FOUND_SEAT_GRADE(HttpStatus.BAD_REQUEST.value(), "찾을 수 없는 등급 좌석입니다."),
    SEAT_GRADE_ALREADY_SOLD_OUT(HttpStatus.BAD_REQUEST.value(), "이미 매진된 좌석입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;

}
