package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.errorcode.ErrorCode;

public class UserNotFoundException extends GlobalNotFoundException {

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
