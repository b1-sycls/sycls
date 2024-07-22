package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {

    CATEGORY_NAME_DUPLICATED(HttpStatus.BAD_REQUEST.value(), "중복된 카테고리 이름 입니다. ");

    private final Integer httpStatusCode;
    private final String description;
}
