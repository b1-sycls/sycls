package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatGradeAlreadySoldOutException extends GlobalStatusException {

    public SeatGradeAlreadySoldOutException(ErrorCode errorCode) {
        super(errorCode);
    }
}
