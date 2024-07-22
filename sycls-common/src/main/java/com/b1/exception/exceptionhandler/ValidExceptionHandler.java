package com.b1.exception.exceptionhandler;

import com.b1.exception.errorcode.CommonErrorCode;
import com.b1.globalresponse.ErrorResponseDto;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice
public class ValidExceptionHandler {

    /**
     * 유효성 검사에서 예외가 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> validException(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException] cause : {}, message : {} ",
                NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());

        ConcurrentHashMap<Object, Object> validationMessage = new ConcurrentHashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            validationMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponseDto errorResponseDto = ErrorResponseDto
                .of(CommonErrorCode.BAD_REQUEST, validationMessage);

        return ResponseEntity
                .status(CommonErrorCode.BAD_REQUEST.getHttpStatusCode())
                .body(errorResponseDto);
    }

}
