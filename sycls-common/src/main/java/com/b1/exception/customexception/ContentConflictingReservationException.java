package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalReservationException;
import com.b1.exception.errorcode.ErrorCode;

public class ContentConflictingReservationException extends GlobalReservationException {

    public ContentConflictingReservationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
