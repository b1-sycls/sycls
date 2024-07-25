package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatAlreadyDeletedException extends GlobalStatusException {

    public SeatAlreadyDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
