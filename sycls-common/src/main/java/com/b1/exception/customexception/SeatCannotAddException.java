package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalCannotAddException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatCannotAddException extends GlobalCannotAddException {

    public SeatCannotAddException(ErrorCode errorCode) {
        super(errorCode);
    }

}
