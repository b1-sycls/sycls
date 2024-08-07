package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalEmailException;
import com.b1.exception.errorcode.ErrorCode;

public class EmailSendFailException extends GlobalEmailException {

    public EmailSendFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
