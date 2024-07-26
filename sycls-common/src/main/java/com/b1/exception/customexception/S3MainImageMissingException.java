package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalMissingException;
import com.b1.exception.errorcode.ErrorCode;

public class S3MainImageMissingException extends GlobalMissingException {

    public S3MainImageMissingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
