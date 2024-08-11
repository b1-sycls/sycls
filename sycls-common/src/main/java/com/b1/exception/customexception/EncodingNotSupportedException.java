package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalEmailException;
import com.b1.exception.errorcode.ErrorCode;

public class EncodingNotSupportedException extends GlobalEmailException {

    public EncodingNotSupportedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
