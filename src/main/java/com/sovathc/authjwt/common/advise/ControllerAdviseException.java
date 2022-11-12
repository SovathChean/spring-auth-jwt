package com.sovathc.authjwt.common.advise;

import com.sovathc.authjwt.common.exception.AbstractException;
import com.sovathc.authjwt.common.exception.BusinessException;
import com.sovathc.authjwt.common.type.SysHttpResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdviseException extends AbstractException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAnyException(Exception ex)
    {
        String message = "Something went wrong";
        if(ex instanceof BusinessException)
        {
            logger.error("BusinessException exception: {}", ex);
            return buildExceptionResponse(ex, HttpStatus.BAD_REQUEST, ((BusinessException) ex).getCode());
        }
        else if(ex instanceof RuntimeException)
        {
            logger.error("Runtime exception: {}", ex);
            return buildExceptionResponse(ex, HttpStatus.BAD_REQUEST, SysHttpResultCode.ERROR_400.getCode());
        }

        logger.error("Internal server exception: {}", ex);
        return buildExceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, SysHttpResultCode.ERROR_500.getCode());
    }
}
