package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalEmailServiceException;
import com.b1.exception.errorcode.ErrorCode;

public class EmailSendFailException extends GlobalEmailServiceException {

    public EmailSendFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
