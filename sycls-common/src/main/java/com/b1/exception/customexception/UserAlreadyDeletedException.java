package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class UserAlreadyDeletedException extends GlobalStatusException {
    public UserAlreadyDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }
}