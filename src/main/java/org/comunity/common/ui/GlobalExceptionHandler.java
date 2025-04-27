package org.comunity.common.ui;

import lombok.extern.slf4j.Slf4j;
import org.comunity.common.domain.exception.ErrorCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 에러발생시 받을 수 있는 ExceptionHandler
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Response<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return Response.error(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(Exception.class)
    public Response<Void> handleException(Exception e) {
        log.error(e.getMessage());
        return Response.error(ErrorCode.INVALID_INPUT_VALUE);
    }
}
