package com.b1.globalresponse;

import com.b1.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponseDto<T> extends RestApiResponseDto {

    protected ErrorResponseDto(int code, String message) {
        super(code, message, null);
    }

    protected ErrorResponseDto(int code, String message, T data) {
        super(code, message, data);
    }

    public static <T> ErrorResponseDto of(ErrorCode errorCode) {
        return new ErrorResponseDto<>(
                errorCode.getHttpStatusCode(),
                errorCode.getDescription());
    }

    public static <T> ErrorResponseDto of(ErrorCode errorCode, T data) {
        return new ErrorResponseDto<>(
                errorCode.getHttpStatusCode(),
                errorCode.getDescription(),
                data);
    }
}
