package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalEntityInUseException;
import com.b1.exception.errorcode.ErrorCode;

public class CategoryInUseException extends GlobalEntityInUseException {

    public CategoryInUseException(ErrorCode errorCode) {
        super(errorCode);
    }
}
