package com.b1.exception.customexception.global;

import com.b1.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalCannotAddExcpetion extends RuntimeException {

    private final ErrorCode errorCode;

    public GlobalCannotAddExcpetion(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

}
