package com.b1.exception.customexception;

import com.b1.exception.errorcode.ErrorCode;

public class InvalidDateTimeException extends InvalidDateException {

    public InvalidDateTimeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
