package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SeatGradeErrorCode implements ErrorCode {

    NOT_FOUND_SEAT_GRADE(HttpStatus.NOT_FOUND.value(), "찾을 수 없는 등급 좌석입니다."),
    DUPLICATED_SEAT_GRADE(HttpStatus.BAD_REQUEST.value(), "중복되는 좌석등급입니다."),
    CANNOT_UPDATE_SEAT_GRADE(HttpStatus.BAD_REQUEST.value(), "예매가 진행되어 회차등급을 수정할 수 없습니다."),
    SEAT_GRADE_ALREADY_SOLD_OUT(HttpStatus.BAD_REQUEST.value(), "이미 매진된 좌석입니다."),
    SEAT_GRADE_ALREADY_DELETED(HttpStatus.BAD_REQUEST.value(), "이미 삭제된 등급 좌석입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;

}
