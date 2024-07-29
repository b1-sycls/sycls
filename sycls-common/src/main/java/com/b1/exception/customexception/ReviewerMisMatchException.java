package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalMismatchException;
import com.b1.exception.errorcode.ErrorCode;

public class ReviewerMisMatchException extends GlobalMismatchException {

    public ReviewerMisMatchException(ErrorCode errorCode) {
        super(errorCode);
    }

}
