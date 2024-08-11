package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalMismatchException;
import com.b1.exception.errorcode.ErrorCode;

public class UserIncorrectPasswordException extends GlobalMismatchException {
    public UserIncorrectPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}