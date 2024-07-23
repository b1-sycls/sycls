package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.errorcode.ErrorCode;

public class CategoryNotFoundException extends GlobalNotFoundException {

    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
