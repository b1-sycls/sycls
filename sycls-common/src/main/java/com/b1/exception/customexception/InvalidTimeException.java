package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalInvalidException;
import com.b1.exception.errorcode.ErrorCode;

public class InvalidTimeException extends GlobalInvalidException {

    public InvalidTimeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
