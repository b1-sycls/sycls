package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatGradeNotFoundException extends GlobalNotFoundException {

    public SeatGradeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
