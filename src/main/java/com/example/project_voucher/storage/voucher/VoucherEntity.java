package com.example.project_voucher.storage.voucher;

import com.example.project_voucher.common.type.VoucherAmountType;
import com.example.project_voucher.common.type.VoucherStatusType;
import com.example.project_voucher.storage.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "voucher")
@Entity
public class VoucherEntity extends BaseEntity {
    private String code;

    @Enumerated(EnumType.STRING)
    private VoucherStatusType status; // 상품권상태 - Enum 클래스
    private LocalDate validFrom; // 유효기간 - 굳이 시간까지 보여줄 필요없어서
    private LocalDate validTo;

    @Enumerated(EnumType.STRING)
    private VoucherAmountType amount;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "voucher_id")
    private List<VoucherHistoryEntity> histories = new ArrayList<>();
    public VoucherEntity() {
    }

    public VoucherEntity(String code, VoucherStatusType status, LocalDate validFrom, LocalDate validTo, VoucherAmountType amount, VoucherHistoryEntity voucherHistoryEntity) {
        this.code = code;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.amount = amount;

        this.histories.add(voucherHistoryEntity); // 발행시 기록 추가
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

    public VoucherAmountType getAmount() {
        return amount;
    }

    public List<VoucherHistoryEntity> getHistories() {
        return histories;
    }

    // 사용권을 사용 불가 처리
    public void disable(final VoucherHistoryEntity voucherHistoryEntity) {
        if(!this.status.equals(VoucherStatusType.PUBLISH)){
            throw new IllegalStateException("사용 불가 처리할 수 없는 상태의 상품권 입니다.");
        }
        this.status = VoucherStatusType.DISABLE;
        this.histories.add(voucherHistoryEntity);
    }

    public void use(final VoucherHistoryEntity voucherHistoryEntity) {
        if (!this.status.equals(VoucherStatusType.PUBLISH)) {
            throw new IllegalStateException("사용 할 수 없는 상태의 상품권 입니다.");
        }
        this.status = VoucherStatusType.USE;
        this.histories.add(voucherHistoryEntity);
    }
}
