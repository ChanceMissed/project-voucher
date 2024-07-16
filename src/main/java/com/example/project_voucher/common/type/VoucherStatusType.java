package com.example.project_voucher.common.type;

/**
 *  Entity 와 Controller 에서 공통으로 사용할거라
 *  common + enum으로 뺏음
 */
public enum VoucherStatusType {
    PUBLISH, // 발행
    DISABLE, // 폐기 - 환불같은
    USE // 사용
}
