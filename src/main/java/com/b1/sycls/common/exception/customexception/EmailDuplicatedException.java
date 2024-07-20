package com.b1.sycls.common.exception.customexception;

import com.b1.sycls.common.exception.customexception.global.GlobalDuplicatedException;
import com.b1.sycls.common.exception.errorcode.ErrorCode;

public class EmailDuplicatedException extends GlobalDuplicatedException {

    public EmailDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }

}
