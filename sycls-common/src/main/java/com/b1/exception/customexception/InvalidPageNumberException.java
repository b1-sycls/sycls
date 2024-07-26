package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalInvalidException;
import com.b1.exception.errorcode.ErrorCode;

public class InvalidPageNumberException extends GlobalInvalidException {

    public InvalidPageNumberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
