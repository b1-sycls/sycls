package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalInvalidException;
import com.b1.exception.errorcode.ErrorCode;

public class S3InvalidImageTypeException extends GlobalInvalidException {

    public S3InvalidImageTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
