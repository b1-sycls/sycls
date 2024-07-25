package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class BookingNotAvailableException extends GlobalStatusException {

    public BookingNotAvailableException(ErrorCode errorCode) {
        super(errorCode);
    }
}
