package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalFailException;
import com.b1.exception.errorcode.ErrorCode;

public class EmailSendFailException extends GlobalFailException {

    public EmailSendFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
