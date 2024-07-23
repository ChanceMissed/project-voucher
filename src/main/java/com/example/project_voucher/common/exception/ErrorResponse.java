package com.example.project_voucher.common.exception;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * e.getMessage 가 너무 text/plain; 하게 나오니까 
 * wrapping 해서 반환해주기
 *  추가 변경사항
 *  1. 타임스탬프
 *  2. 한글메시지를 코드로
 * @param message
 */
public record ErrorResponse(
    String message,
    LocalDateTime timeStamp,
    UUID traceId // 이 에러로 외부에서 이런 traceId 를 가지고있어요~ 하고 에러추적
) {

}
