package com.b1.sycls.common.exception.customexception.global;

import com.b1.sycls.common.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public abstract class GlobalDuplicatedException extends RuntimeException {

    private final ErrorCode errorCode;

    public GlobalDuplicatedException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
