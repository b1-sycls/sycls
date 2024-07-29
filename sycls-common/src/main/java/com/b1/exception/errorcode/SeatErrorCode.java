package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SeatErrorCode implements ErrorCode {

    NOT_FOUND_SEAT(HttpStatus.NOT_FOUND.value(), "좌석을 찾을 수 없습니다."),
    DUPLICATED_SEAT(HttpStatus.BAD_REQUEST.value(), "이미 등록된 좌석 코드입니다."),
    CANNOT_ADD_SEAT(HttpStatus.BAD_REQUEST.value(), "더 이상 좌석을 등록할 수 없습니다."),
    ALREADY_DELETED_SEAT(HttpStatus.BAD_REQUEST.value(), "이미 삭제된 좌석입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
