package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatGradeAlreadyDeletedException extends GlobalStatusException {

    public SeatGradeAlreadyDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }

}
