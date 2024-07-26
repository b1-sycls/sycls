package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalLoadingException;
import com.b1.exception.errorcode.ErrorCode;

public class S3UploadingException extends GlobalLoadingException {

    public S3UploadingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
