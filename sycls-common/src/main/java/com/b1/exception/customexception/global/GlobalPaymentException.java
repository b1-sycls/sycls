package com.b1.exception.customexception.global;

import com.b1.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalPaymentException extends RuntimeException {
    private final ErrorCode errorCode;

    public GlobalPaymentException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
