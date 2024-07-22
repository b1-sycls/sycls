package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalAlreadyDeletedException;
import com.b1.exception.errorcode.ErrorCode;

public class PlaceAlreadyDeletedException extends GlobalAlreadyDeletedException {

    public PlaceAlreadyDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }

}
