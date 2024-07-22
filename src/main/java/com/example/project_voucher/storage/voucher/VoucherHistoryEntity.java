package com.example.project_voucher.storage.voucher;

import com.example.project_voucher.common.type.RequesterType;
import com.example.project_voucher.common.type.VoucherAmountType;
import com.example.project_voucher.common.type.VoucherStatusType;
import com.example.project_voucher.storage.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * 발행된 상품권의 상태를 기록하는 엔티티
 */
@Table(name = "voucher_history")
@Entity
public class VoucherHistoryEntity extends BaseEntity {
    private String orderId; // 서버 승인 , 주문 (고유번호)

    @Enumerated(EnumType.STRING)
    private RequesterType requesterType;

    private String requestId;

    @Enumerated(EnumType.STRING)
    private VoucherStatusType status;
    private String description;

    public VoucherHistoryEntity() {
    }

    public VoucherHistoryEntity(String orderId, RequesterType requesterType, String requestId, VoucherStatusType status, String description) {
        this.orderId = orderId;
        this.requesterType = requesterType;
        this.requestId = requestId;
        this.status = status;
        this.description = description;
    }

    public String getOrderId() {
        return orderId;
    }

    public RequesterType getRequesterType() {
        return requesterType;
    }

    public String getRequestId() {
        return requestId;
    }

    public VoucherStatusType getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
