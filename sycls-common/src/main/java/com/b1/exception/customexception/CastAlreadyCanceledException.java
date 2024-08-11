package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class CastAlreadyCanceledException extends GlobalStatusException {

    public CastAlreadyCanceledException(ErrorCode errorCode) {
        super(errorCode);
    }
}
