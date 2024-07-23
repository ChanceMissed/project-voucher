package com.example.project_voucher.common.exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 외부에서 들어오는 API 들을 위한 공통 예외처리 1. 커스텀 예외를 하면 장점 - ex DB Connection 에러가 발행했다 -   e.printStackTrace()
 * Connection 에 민감한 정보들을 커스텀 해서 사용자 친화적으로 Logger log 메시지를 보여주고 직관적인 에러를 잡을 수 있음 - 단점, 너무 하나하나 만들 필요
 * 없다! 기본 제공 예외로 충분히 처리가능하다면 그걸로 처리하기
 */
@RestControllerAdvice
public class ApiControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ApiControllerAdvice.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(final IllegalArgumentException e) {
        log.info(Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(e.getMessage(), LocalDateTime.now(), UUID.randomUUID());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResponse handleIllegalStatementException(final IllegalStateException e) {
        log.info(Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(e.getMessage(), LocalDateTime.now(), UUID.randomUUID());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(final Exception e) {
        log.error(Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(e.getMessage(), LocalDateTime.now(), UUID.randomUUID());
    }
}
