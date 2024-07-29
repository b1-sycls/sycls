package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalCannotAddExcpetion;
import com.b1.exception.errorcode.ErrorCode;

public class ReviewCannotAddException extends GlobalCannotAddExcpetion {

    public ReviewCannotAddException(ErrorCode errorCode) {
        super(errorCode);
    }

}
