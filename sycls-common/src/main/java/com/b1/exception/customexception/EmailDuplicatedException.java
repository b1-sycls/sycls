package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalDuplicatedException;
import com.b1.exception.errorcode.ErrorCode;

public class EmailDuplicatedException extends GlobalDuplicatedException {

    public EmailDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }

}
