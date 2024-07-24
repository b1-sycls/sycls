package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatNotFoundException extends GlobalNotFoundException {

    public SeatNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
