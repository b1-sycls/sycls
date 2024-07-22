package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.errorcode.ErrorCode;

public class CateGoryNotFoundException extends GlobalNotFoundException {

    public CateGoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
