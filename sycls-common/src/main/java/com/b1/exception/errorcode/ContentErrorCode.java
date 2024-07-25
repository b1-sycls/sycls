package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ContentErrorCode implements ErrorCode {

    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "공연 정보를 찾지 못하였습니다."),
    CONTENT_STATUS_EQUALS(HttpStatus.BAD_REQUEST.value(), "공연의 상태가 동일합니다"),
    CONTENT_NOT_CHANGE_STATUS(HttpStatus.BAD_REQUEST.value(), "활성화된 회차가 없어 활성화 상태로 변경할 수 없습니다.");

    private final Integer httpStatusCode;
    private final String description;
}
