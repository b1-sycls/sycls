package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalEmailServiceException;
import com.b1.exception.errorcode.ErrorCode;

public class EmailCodeException extends GlobalEmailServiceException {

    public EmailCodeException(ErrorCode errorCode) {
        super(errorCode);
    }
}