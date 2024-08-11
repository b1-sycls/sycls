package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalCannotAddException;
import com.b1.exception.errorcode.ErrorCode;

public class ReviewCannotAddException extends GlobalCannotAddException {

    public ReviewCannotAddException(ErrorCode errorCode) {
        super(errorCode);
    }

}
