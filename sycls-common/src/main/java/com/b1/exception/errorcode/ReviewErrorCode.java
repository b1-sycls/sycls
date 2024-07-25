package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {

    NOT_FOUND_REVIEW(HttpStatus.BAD_REQUEST.value(), "리뷰를 찾을 수 없습니다."),
    ALREADY_DELETED(HttpStatus.BAD_REQUEST.value(), "이미 삭제된 리뷰입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;

}
