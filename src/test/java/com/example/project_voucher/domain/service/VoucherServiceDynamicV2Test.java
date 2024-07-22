package com.example.project_voucher.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.example.project_voucher.common.dto.RequestContext;
import com.example.project_voucher.common.type.RequesterType;
import com.example.project_voucher.common.type.VoucherAmountType;
import com.example.project_voucher.common.type.VoucherStatusType;
import com.example.project_voucher.storage.voucher.VoucherEntity;
import com.example.project_voucher.storage.voucher.VoucherRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VoucherServiceDynamicV2Test {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    @TestFactory
    Stream<DynamicTest> test() {
        final List<String> codes = new ArrayList<>();

        return Stream.of(
            dynamicTest("[0]상품권을 발행합니다", () -> {
                // given
                final RequestContext requestContext = new RequestContext(RequesterType.PARTNER,
                    UUID.randomUUID().toString());
                final LocalDate validFrom = LocalDate.now();
                final LocalDate validTo = LocalDate.now().plusDays(30);
                final VoucherAmountType amount = VoucherAmountType.KRW_30000;

                //when
                final String code = voucherService.publishV2(requestContext, validFrom, validTo,
                    amount);
                codes.add(code);

                //then
                final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();
                assertThat(voucherEntity.getStatus()).isEqualTo(VoucherStatusType.PUBLISH);
            }),
            dynamicTest("[0]상품권을 사용 불가 처리 합니다.", () -> {
                // given
                final RequestContext requestContext = new RequestContext(RequesterType.PARTNER,
                    UUID.randomUUID().toString());
                final String code = codes.get(0);

                // when
                voucherService.disableV2(requestContext, code);

                // then
                final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();
                assertThat(voucherEntity.getStatus()).isEqualTo(VoucherStatusType.DISABLE);
            }),
            dynamicTest("[0]사용 불가 처리한 상품권을 사용 할 수 없습니다", () -> {
                // given
                final RequestContext requestContext = new RequestContext(RequesterType.PARTNER,
                    UUID.randomUUID().toString());
                final String code = codes.get(0);

                // when
                assertThatThrownBy(() -> voucherService.useV2(requestContext, code))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("사용 할 수 없는 상태의 상품권 입니다.");

                // then
                final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();
                assertThat(voucherEntity.getStatus()).isEqualTo(VoucherStatusType.DISABLE);
            }),
            dynamicTest("[1] 상품권을 사용 합니다.", () -> {
                // given
                final RequestContext requestContext = new RequestContext(RequesterType.PARTNER,
                    UUID.randomUUID().toString());
                final LocalDate validFrom = LocalDate.now();
                final LocalDate validTo = LocalDate.now().plusDays(30);
                final VoucherAmountType amount = VoucherAmountType.KRW_30000;

                final String code = voucherService.publishV2(requestContext, validFrom, validTo,
                    amount);
                codes.add(code);

                // when
                voucherService.useV2(requestContext, code);

                // then
                final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();
                assertThat(voucherEntity.getStatus()).isEqualTo(VoucherStatusType.USE);
            }),
            dynamicTest("[1] 사용한 상품권은 사용 불가 처리 할 수 없습니다.", () -> {
                // given
                final RequestContext requestContext = new RequestContext(RequesterType.PARTNER,
                    UUID.randomUUID().toString());
                final String code = codes.get(1);

                // when
                assertThatThrownBy(() -> voucherService.disableV2(requestContext, code))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("사용 불가 처리할 수 없는 상태의 상품권 입니다.");

                // then
                final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();
                assertThat(voucherEntity.getStatus()).isEqualTo(VoucherStatusType.USE);
            }),
            dynamicTest("[1] 사용한 상품권은 또 사용할 수 없습니다.", () -> {
                // given
                final RequestContext requestContext = new RequestContext(RequesterType.PARTNER,
                    UUID.randomUUID().toString());
                final String code = codes.get(1);

                // when
                assertThatThrownBy(() -> voucherService.useV2(requestContext, code))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("사용 할 수 없는 상태의 상품권 입니다.");

                // then
                final VoucherEntity voucherEntity = voucherRepository.findByCode(code).get();
                assertThat(voucherEntity.getStatus()).isEqualTo(VoucherStatusType.USE);
            })
        );
    }
}
