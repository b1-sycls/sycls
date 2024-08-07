package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalEmailException;
import com.b1.exception.errorcode.ErrorCode;

public class EmailCodeException extends GlobalEmailException {

    public EmailCodeException(ErrorCode errorCode) {
        super(errorCode);
    }
}