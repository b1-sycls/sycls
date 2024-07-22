package com.b1.globalresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestApiResponseDto<T> {

    private static final String SUCCESS = "성공";

    private final int code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    protected RestApiResponseDto(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> RestApiResponseDto<T> of(T data) {
        return new RestApiResponseDto<>(HttpStatus.OK.value(), SUCCESS, data);
    }

    public static <T> RestApiResponseDto<T> of(String message) {
        return new RestApiResponseDto<>(HttpStatus.OK.value(), message, null);
    }

    public static <T> RestApiResponseDto<T> of(int code, String message) {
        return new RestApiResponseDto<>(code, message, null);
    }

    public static <T> RestApiResponseDto<T> of(String message, T data) {
        return new RestApiResponseDto<>(HttpStatus.OK.value(), message, data);
    }

    public static <T> RestApiResponseDto<T> of(int code, T data) {
        return new RestApiResponseDto<>(code, SUCCESS, data);
    }


    public static <T> RestApiResponseDto<T> of(int code, String message, T data) {
        return new RestApiResponseDto<>(code, message, data);
    }
}
