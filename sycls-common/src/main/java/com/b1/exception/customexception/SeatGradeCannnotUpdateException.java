package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class SeatGradeCannnotUpdateException extends GlobalStatusException {

    public SeatGradeCannnotUpdateException(ErrorCode errorCode) {
        super(errorCode);
    }

}