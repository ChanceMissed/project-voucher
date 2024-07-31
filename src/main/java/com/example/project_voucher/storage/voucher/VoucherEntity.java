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

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id")
    private ContractEntity contract; // 상품권들은 하나의 계약으로 만들어져 있음


    public VoucherEntity() {
    }

//    public VoucherEntity(String code, VoucherStatusType status, LocalDate validFrom, LocalDate validTo, VoucherAmountType amount, VoucherHistoryEntity voucherHistoryEntity) {
    public VoucherEntity(String code, VoucherStatusType status, VoucherAmountType amount, VoucherHistoryEntity voucherHistoryEntity, ContractEntity contractEntity) {
        this.code = code;
        this.status = status;
        this.validFrom = LocalDate.now();
        this.validTo = LocalDate.now().plusDays(contractEntity.getVoucherValidPeriodDayCount());
        this.amount = amount;

        this.histories.add(voucherHistoryEntity); // 발행시 기록 추가
        this.contract = contractEntity;
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

    public VoucherHistoryEntity publishHistory(){
        return histories.stream()
            .filter(voucherHistoryEntity -> voucherHistoryEntity.getStatus()
                .equals(VoucherStatusType.PUBLISH))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("발행 이력이 존재하지 않습니다."));
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
