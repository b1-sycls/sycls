package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.errorcode.ErrorCode;

public class ContentNotFoundException extends GlobalNotFoundException {

    public ContentNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
