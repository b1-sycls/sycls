package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class ContentNotChangeStatusException extends GlobalStatusException {

    public ContentNotChangeStatusException(ErrorCode errorCode) {
        super(errorCode);
    }
}
