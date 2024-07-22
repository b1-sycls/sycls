package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalAlreadyDeletedException;
import com.b1.exception.errorcode.ErrorCode;

public class CategoryAlreadyDeletedException extends GlobalAlreadyDeletedException {

    public CategoryAlreadyDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
