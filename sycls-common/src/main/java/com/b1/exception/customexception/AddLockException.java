package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalInterruptedException;
import com.b1.exception.errorcode.ErrorCode;

public class AddLockException extends GlobalInterruptedException {

    public AddLockException(ErrorCode errorCode) {
        super(errorCode);
    }

}
