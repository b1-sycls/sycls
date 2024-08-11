package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalDuplicatedException;
import com.b1.exception.errorcode.ErrorCode;

public class UserNicknameDuplicatedException extends GlobalDuplicatedException {

    public UserNicknameDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
