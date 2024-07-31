package com.example.project_voucher.storage.voucher;

import com.example.project_voucher.common.type.VoucherAmountType;
import com.example.project_voucher.common.type.VoucherStatusType;
import com.example.project_voucher.storage.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "contract")
@Entity
public class ContractEntity extends BaseEntity {
    private String code; // 계약의 고유 코드
    private LocalDate validFrom; // 계약의 유효 기간 시작일
    private LocalDate validTo; // 계약의 유효 기간 종료일
    private Integer voucherValidPeriodDayCount; // 상품권 유효기간 일자

    // 2/22 일에 상품권을 발행할 수 있는가? (1/10 ~ 3/10)
    // 2/22 일에 발행한 상품권은 언제까지 쓸 수 있는가? voucherValidPeriodDayCount = 180일


    public ContractEntity() {
    }

    public ContractEntity(String code, LocalDate validFrom, LocalDate validTo,
        Integer voucherValidPeriodDayCount) {
        this.code = code;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.voucherValidPeriodDayCount = voucherValidPeriodDayCount;
    }


    public Boolean isExpired(){
        return LocalDate.now().isAfter(validTo);
    }

    public String getCode() {
        return code;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public Integer getVoucherValidPeriodDayCount() {
        return voucherValidPeriodDayCount;
    }
}
