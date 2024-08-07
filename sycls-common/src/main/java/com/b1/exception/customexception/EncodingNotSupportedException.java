package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalEmailServiceException;
import com.b1.exception.errorcode.ErrorCode;

public class EncodingNotSupportedException extends GlobalEmailServiceException {

    public EncodingNotSupportedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
