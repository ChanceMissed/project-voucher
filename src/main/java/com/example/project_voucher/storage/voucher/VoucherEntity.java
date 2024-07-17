package com.example.project_voucher.storage.voucher;

import com.example.project_voucher.common.type.VoucherStatusType;
import com.example.project_voucher.storage.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Table(name = "voucher")
@Entity
public class VoucherEntity extends BaseEntity {
    private String code;
    private VoucherStatusType status; // 상품권상태 - Enum 클래스
    private LocalDate validFrom; // 유효기간 - 굳이 시간까지 보여줄 필요없어서
    private LocalDate validTo;
    private Long amount;

    public VoucherEntity() {
    }

    public VoucherEntity(String code, VoucherStatusType status, LocalDate validFrom, LocalDate validTo, Long amount) {
        this.code = code;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public VoucherStatusType getStatus() {
        return status;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public Long getAmount() {
        return amount;
    }

    // 사용권을 사용 불가 처리
    public void disable() {
        this.status = VoucherStatusType.DISABLE;
    }

    public void use() {
        this.status = VoucherStatusType.USE;
    }
}
