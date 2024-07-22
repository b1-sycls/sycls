package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class CategoryAlreadyEnableException extends GlobalStatusException {

    public CategoryAlreadyEnableException(ErrorCode errorCode) {
        super(errorCode);
    }
}
