package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalDuplicatedException;
import com.b1.exception.errorcode.ErrorCode;

public class CategoryNameDuplicatedException extends GlobalDuplicatedException {

    public CategoryNameDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
