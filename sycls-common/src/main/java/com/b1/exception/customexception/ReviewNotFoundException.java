package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.errorcode.ErrorCode;

public class ReviewNotFoundException extends GlobalNotFoundException {

    public ReviewNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
