package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatReservationLogNotAvailableException extends GlobalStatusException {

    public SeatReservationLogNotAvailableException(ErrorCode errorCode) {
        super(errorCode);
    }
}
