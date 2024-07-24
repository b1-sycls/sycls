package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class RoundStatusEqualsException extends GlobalStatusException {

    public RoundStatusEqualsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
