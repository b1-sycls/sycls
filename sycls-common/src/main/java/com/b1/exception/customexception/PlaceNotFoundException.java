package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.errorcode.ErrorCode;

public class PlaceNotFoundException extends GlobalNotFoundException {

    public PlaceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
