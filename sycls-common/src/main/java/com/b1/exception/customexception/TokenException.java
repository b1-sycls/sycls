package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class TokenException extends GlobalStatusException {
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}