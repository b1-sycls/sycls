package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class RoundNotFullSeatGradeException extends GlobalStatusException {

    public RoundNotFullSeatGradeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
