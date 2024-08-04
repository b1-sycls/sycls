package com.b1.exception.customexception;

import com.b1.exception.customexception.global.GlobalPaymentException;
import com.b1.exception.errorcode.ErrorCode;

public class TossPaymentException extends GlobalPaymentException {
    public TossPaymentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
