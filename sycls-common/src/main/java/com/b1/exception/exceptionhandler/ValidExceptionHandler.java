package com.b1.exception.exceptionhandler;

import com.b1.exception.errorcode.CommonErrorCode;
import com.b1.exception.errorcode.S3ErrorCode;
import com.b1.globalresponse.ErrorResponseDto;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j(topic = "Valid Exception Handler")
@Order(1)
@RestControllerAdvice
public class ValidExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseDto> imageMaxUpSizeExceeded(
            MaxUploadSizeExceededException e) {
        log.info("[MaxUploadSizeExceededException] cause : {}, message : {} ",
                NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());

        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(S3ErrorCode.S3_IMAGE_SIZE_MAX);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponseDto);
    }


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
                .of(CommonErrorCode.BAD_REQUEST, "valid", validationMessage);

        return ResponseEntity
                .status(CommonErrorCode.BAD_REQUEST.getHttpStatusCode())
                .body(errorResponseDto);
    }
}
