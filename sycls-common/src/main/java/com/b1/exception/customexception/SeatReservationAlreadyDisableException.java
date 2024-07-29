package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatReservationAlreadyDisableException extends GlobalStatusException {

    public SeatReservationAlreadyDisableException(
            ErrorCode errorCode) {
        super(errorCode);
    }
}
