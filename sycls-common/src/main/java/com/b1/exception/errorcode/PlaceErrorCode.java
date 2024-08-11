package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PlaceErrorCode implements ErrorCode {

    NOT_FOUND_PLACE(HttpStatus.NOT_FOUND.value(), "공연장을 찾을 수 없습니다."),
    ALREADY_DELETED(HttpStatus.BAD_REQUEST.value(), "이미 삭제된 공연장입니다."),
    CANNOT_UPDATE_PLACE(HttpStatus.BAD_REQUEST.value(), "공연장 정보를 수정할 수 없습니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;

}
