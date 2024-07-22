package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class CategoryAlreadyDisableException extends GlobalStatusException {

    public CategoryAlreadyDisableException(ErrorCode errorCode) {
        super(errorCode);
    }
}