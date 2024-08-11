package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalDuplicatedException;
import com.b1.exception.errorcode.ErrorCode;

public class UserEmailDuplicatedException extends GlobalDuplicatedException {

    public UserEmailDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
