package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class ReviewAlreadyDeletedException extends GlobalStatusException {

    public ReviewAlreadyDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }

}
