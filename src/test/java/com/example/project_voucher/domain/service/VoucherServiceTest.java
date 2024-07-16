package com.example.project_voucher.domain.service;

import com.example.project_voucher.common.type.VoucherStatusType;
import com.example.project_voucher.storage.voucher.VoucherEntity;
import com.example.project_voucher.storage.voucher.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoucherServiceTest {
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    @DisplayName("발행된 상품권은 code 로 조회할 수 있어야 된다.")
    @Test
    public void test1(){
        // given
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);
        final Long amount = 10000L;

        final String code = voucherService.publish(validFrom, validTo, amount);

        // when
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();

        // then
        Assertions.assertThat(voucherEntity).isNotNull();
        Assertions.assertThat(voucherEntity.getCode()).isEqualTo(code);
        Assertions.assertThat(voucherEntity.getStatus()).isEqualTo(VoucherStatusType.PUBLISH);
        Assertions.assertThat(voucherEntity.getValidTo()).isEqualTo(validTo);

    }
}