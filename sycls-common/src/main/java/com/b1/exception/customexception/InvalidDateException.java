package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalInvalidException;
import com.b1.exception.errorcode.ErrorCode;

public class InvalidDateException extends GlobalInvalidException {

    public InvalidDateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
