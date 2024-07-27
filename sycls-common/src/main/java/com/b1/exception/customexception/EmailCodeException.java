package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class EmailCodeException extends GlobalStatusException {

    public EmailCodeException(ErrorCode errorCode) {
        super(errorCode);
    }
}