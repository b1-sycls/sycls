package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalDuplicatedException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatGradeDuplicatedException extends GlobalDuplicatedException {

    public SeatGradeDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }

}
