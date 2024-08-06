package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotSupportedException;
import com.b1.exception.errorcode.ErrorCode;

public class EncodingNotSupportedException extends GlobalNotSupportedException {

    public EncodingNotSupportedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
