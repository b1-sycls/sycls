package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;

public class PlaceCannotUpdateException extends GlobalStatusException {

    public PlaceCannotUpdateException(ErrorCode errorCode) {
        super(errorCode);
    }

}
