package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class ContentStatusEqualsException extends GlobalStatusException {

    public ContentStatusEqualsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
