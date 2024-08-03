package com.b1.exception.exceptionhandler;

import com.b1.exception.customexception.global.GlobalCannotAddExcpetion;
import com.b1.exception.customexception.global.GlobalDuplicatedException;
import com.b1.exception.customexception.global.GlobalEntityInUseException;
import com.b1.exception.customexception.global.GlobalInvalidException;
import com.b1.exception.customexception.global.GlobalLoadingException;
import com.b1.exception.customexception.global.GlobalMissingException;
import com.b1.exception.customexception.global.GlobalNotFoundException;
import com.b1.exception.customexception.global.GlobalPaymentException;
import com.b1.exception.customexception.global.GlobalReservationException;
import com.b1.exception.customexception.global.GlobalStatusException;
import com.b1.exception.errorcode.ErrorCode;
import com.b1.globalresponse.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "ApiException")
@Order(1)
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Api 요청에 동작 중 예외가 발생한 경우
     */
    @ExceptionHandler(GlobalDuplicatedException.class)
    protected ResponseEntity<ErrorResponseDto> globalDuplicatedException(
            GlobalDuplicatedException e) {
        log.error("GlobalDuplicatedException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> globalNotFoundException(GlobalNotFoundException e) {
        log.error("GlobalNotFoundException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalEntityInUseException.class)
    protected ResponseEntity<ErrorResponseDto> globalEntityInUseException(
            GlobalEntityInUseException e) {
        log.error("GlobalEntityInUseException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalStatusException.class)
    protected ResponseEntity<ErrorResponseDto> globalStatusException(
            GlobalStatusException e) {
        log.error("GlobalStatusException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalInvalidException.class)
    protected ResponseEntity<ErrorResponseDto> globalInvalidException(GlobalInvalidException e) {
        log.error("GlobalInvalidException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalMissingException.class)
    protected ResponseEntity<ErrorResponseDto> globalMissingException(GlobalMissingException e) {
        log.error("GlobalMissingException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalLoadingException.class)
    protected ResponseEntity<ErrorResponseDto> globalLoadingException(GlobalLoadingException e) {
        log.error("GlobalLoadingException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalReservationException.class)
    protected ResponseEntity<ErrorResponseDto> globalReservationException(
            GlobalReservationException e) {
        log.error("GlobalReservationException 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalCannotAddExcpetion.class)
    protected ResponseEntity<ErrorResponseDto> globalCannotAddException(
            GlobalCannotAddExcpetion e
    ) {
        log.error("GlobalCannotAddExcpetion 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(GlobalPaymentException.class)
    protected ResponseEntity<ErrorResponseDto> globalPaymentException(
            GlobalCannotAddExcpetion e
    ) {
        log.error("GlobalCannotAddExcpetion 발생");
        return sendErrorResponse(e.getErrorCode());
    }

    private ResponseEntity<ErrorResponseDto> sendErrorResponse(ErrorCode e) {
        return ResponseEntity.status(e.getHttpStatusCode())
                .body(ErrorResponseDto.of(e));
    }
}
