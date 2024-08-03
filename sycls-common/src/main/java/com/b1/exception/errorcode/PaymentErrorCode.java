package com.b1.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
    TOSS_PAYMENT_EXCEPTION(HttpStatus.NOT_FOUND.value(), "결제 도중 오류가 발생했습니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
