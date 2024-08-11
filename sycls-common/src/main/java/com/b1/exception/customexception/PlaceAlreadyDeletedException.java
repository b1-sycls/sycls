package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class PlaceAlreadyDeletedException extends GlobalStatusException {

    public PlaceAlreadyDeletedException(ErrorCode errorCode) {
        super(errorCode);
    }

}