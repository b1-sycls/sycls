package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalCannotAddExcpetion;
import com.b1.exception.errorcode.ErrorCode;

public class SeatCannotAddException extends GlobalCannotAddExcpetion {

    public SeatCannotAddException(ErrorCode errorCode) {
        super(errorCode);
    }

}
