package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatReservationLogNotFoundException extends GlobalNotFoundException {

    public SeatReservationLogNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
