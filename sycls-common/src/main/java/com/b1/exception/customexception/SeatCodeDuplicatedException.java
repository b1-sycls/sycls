package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalDuplicatedException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatCodeDuplicatedException extends GlobalDuplicatedException {

    public SeatCodeDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }

}
