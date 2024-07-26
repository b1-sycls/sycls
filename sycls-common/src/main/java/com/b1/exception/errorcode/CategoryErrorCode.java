package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {

    CATEGORY_NAME_DUPLICATED(HttpStatus.CONFLICT.value(), "중복된 카테고리 이름 입니다. "),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "카테고리를 찾지 못하였습니다."),
    CATEGORY_IN_USE(HttpStatus.BAD_REQUEST.value(), "해당 카테고리를 사용하는 공연이 있습니다."),
    CATEGORY_ALREADY_DISABLE(HttpStatus.BAD_REQUEST.value(), "이미 삭제된 카테고리 입니다."),
    CATEGORY_ALREADY_ENABLE(HttpStatus.BAD_REQUEST.value(), "삭제되지 않은 카테고리 입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
