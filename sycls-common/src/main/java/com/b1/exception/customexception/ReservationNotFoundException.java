package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalReservationException;
import com.b1.exception.errorcode.ErrorCode;

public class ReservationNotFoundException extends GlobalReservationException {
    public ReservationNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
