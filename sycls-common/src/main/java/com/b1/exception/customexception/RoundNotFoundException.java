package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.errorcode.ErrorCode;

public class RoundNotFoundException extends GlobalNotFoundException {

    public RoundNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
