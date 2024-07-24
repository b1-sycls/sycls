package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalReservationException;
import com.b1.exception.errorcode.ErrorCode;

public class RoundConflictingReservationException extends GlobalReservationException {

    public RoundConflictingReservationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
