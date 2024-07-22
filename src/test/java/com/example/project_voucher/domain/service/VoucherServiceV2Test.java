package com.example.project_voucher.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.project_voucher.common.dto.RequestContext;
import com.example.project_voucher.common.type.RequesterType;
import com.example.project_voucher.common.type.VoucherAmountType;
import com.example.project_voucher.common.type.VoucherStatusType;
import com.example.project_voucher.storage.voucher.VoucherEntity;
import com.example.project_voucher.storage.voucher.VoucherHistoryEntity;
import com.example.project_voucher.storage.voucher.VoucherRepository;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VoucherServiceV2Test {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    @DisplayName("발행된 상품권은 code 로 조회할 수 있어야 된다.")
    @Test
    public void test1() {
        // given
        final RequestContext requestContext = new RequestContext(RequesterType.PARTNER,
            UUID.randomUUID().toString());
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);
        final VoucherAmountType amount = VoucherAmountType.KRW_30000;

        final String code = voucherService.publishV2(requestContext, validFrom, validTo, amount);

        // when
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();

        // then
        assertThat(voucherEntity).isNotNull();
        assertThat(voucherEntity.getCode()).isEqualTo(code);
        assertThat(voucherEntity.getStatus()).isEqualTo(VoucherStatusType.PUBLISH);
        assertThat(voucherEntity.getValidTo()).isEqualTo(validTo);

        //history
        final VoucherHistoryEntity voucherHistoryEntity = voucherEntity.getHistories().get(0);
        assertThat(voucherHistoryEntity.getOrderId()).isNotNull();
        // 기록된 요청 타입이 - 발행사가 요청한 타입과 같은지
        assertThat(voucherHistoryEntity.getRequesterType()).isEqualTo(requestContext.requesterType());
        assertThat(voucherHistoryEntity.getRequestId()).isEqualTo(requestContext.requesterId());
        assertThat(voucherHistoryEntity.getStatus()).isEqualTo(VoucherStatusType.PUBLISH);
        assertThat(voucherHistoryEntity.getDescription()).isEqualTo("테스트 발행");
    }

    @DisplayName("발행된 상품권은 사용 불가 처리 할 수 있다.")
    @Test
    public void test2() {
        // given
        final RequestContext requestContext = new RequestContext(RequesterType.PARTNER,
            UUID.randomUUID().toString());
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);
        final VoucherAmountType amount = VoucherAmountType.KRW_30000;

        final String code = voucherService.publishV2(requestContext, validFrom, validTo, amount);

        final RequestContext disableRequestContext = new RequestContext(RequesterType.PARTNER,
                UUID.randomUUID().toString());
        // when
        voucherService.disableV2(disableRequestContext,code);
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();

        // then
        assertThat(voucherEntity.getCode()).isEqualTo(code);
        assertThat(voucherEntity.getStatus()).isEqualTo(VoucherStatusType.DISABLE);
        assertThat(voucherEntity.getValidFrom()).isEqualTo(validFrom);
        assertThat(voucherEntity.getValidTo()).isEqualTo(validTo);
        assertThat(voucherEntity.getAmount()).isEqualTo(amount);

        assertThat(voucherEntity.getUpdateAt()).isNotEqualTo(voucherEntity.getCreatedAt());

        //history
        final VoucherHistoryEntity voucherHistoryEntity = voucherEntity.getHistories().get(voucherEntity.getHistories().size() - 1);
        assertThat(voucherHistoryEntity.getOrderId()).isNotNull();
        assertThat(voucherHistoryEntity.getRequesterType()).isEqualTo(disableRequestContext.requesterType());
        assertThat(voucherHistoryEntity.getRequestId()).isEqualTo(disableRequestContext.requesterId());
        assertThat(voucherHistoryEntity.getStatus()).isEqualTo(VoucherStatusType.DISABLE);
        assertThat(voucherHistoryEntity.getDescription()).isEqualTo("테스트 사용 불가");

    }

    @DisplayName("발행된 상품권은 사용 할 수 있다.")
    @Test
    public void test3() {
        // given
        final RequestContext requestContext = new RequestContext(RequesterType.PARTNER,
            UUID.randomUUID().toString());
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);
        final VoucherAmountType amount = VoucherAmountType.KRW_30000;

        final String code = voucherService.publishV2(requestContext, validFrom, validTo, amount);

        final RequestContext useRequestContext = new RequestContext(RequesterType.PARTNER,
                UUID.randomUUID().toString());
        // when
        voucherService.useV2(useRequestContext,code);
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();

        // then
        assertThat(voucherEntity.getCode()).isEqualTo(code);
        assertThat(voucherEntity.getStatus()).isEqualTo(VoucherStatusType.USE);
        assertThat(voucherEntity.getValidFrom()).isEqualTo(validFrom);
        assertThat(voucherEntity.getValidTo()).isEqualTo(validTo);
        assertThat(voucherEntity.getAmount()).isEqualTo(amount);

        assertThat(voucherEntity.getUpdateAt()).isNotEqualTo(voucherEntity.getCreatedAt());

        System.out.println("### voucherEntity.createdAt() = " + voucherEntity.getCreatedAt());
        System.out.println("### voucherEntity.updateAt() = " + voucherEntity.getUpdateAt());

        //history
        final VoucherHistoryEntity voucherHistoryEntity = voucherEntity.getHistories().get(voucherEntity.getHistories().size() - 1);
        assertThat(voucherHistoryEntity.getOrderId()).isNotNull();
        assertThat(voucherHistoryEntity.getRequesterType()).isEqualTo(useRequestContext.requesterType());
        assertThat(voucherHistoryEntity.getRequestId()).isEqualTo(useRequestContext.requesterId());
        assertThat(voucherHistoryEntity.getStatus()).isEqualTo(VoucherStatusType.USE);
        assertThat(voucherHistoryEntity.getDescription()).isEqualTo("테스트 사용");
    }

}