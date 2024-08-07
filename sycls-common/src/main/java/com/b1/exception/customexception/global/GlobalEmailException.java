package com.b1.exception.customexception.global;

import com.b1.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public abstract class GlobalEmailException extends RuntimeException {

    private final ErrorCode errorCode;

    public GlobalEmailException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}