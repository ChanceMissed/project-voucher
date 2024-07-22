package com.example.project_voucher.domain.service;

import com.example.project_voucher.common.dto.RequestContext;
import com.example.project_voucher.common.type.VoucherAmountType;
import com.example.project_voucher.common.type.VoucherStatusType;
import com.example.project_voucher.storage.voucher.VoucherEntity;
import com.example.project_voucher.storage.voucher.VoucherRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    // 상품권 발행
    @Transactional
    public String publish(final LocalDate validFrom, final LocalDate validTo, final VoucherAmountType amount) {
        // 상품권 코드를 UUID 랜덤 16자로 만들어준다.
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherEntity voucherEntity = new VoucherEntity(code, VoucherStatusType.PUBLISH, validFrom, validTo, amount);

        return voucherRepository.save(voucherEntity).getCode();


    }

    // 상품권 사용 불가 처리
    @Transactional
    public void disable(String code) {
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권 입니다."));

        voucherEntity.disable();
    }

    // 상품권 사용
    @Transactional
    public void use(String code) {
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권 입니다."));

        voucherEntity.use();
    }

    // 상품권 발행
    @Transactional
    public String publishV2(final RequestContext requestContext, LocalDate validFrom, final LocalDate validTo, final VoucherAmountType amount) {
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherEntity voucherEntity = new VoucherEntity(code, VoucherStatusType.PUBLISH, validFrom, validTo, amount);

        return voucherRepository.save(voucherEntity).getCode();


    }

    // 상품권 사용 불가 처리
    @Transactional
    public void disableV2(final RequestContext requestContext, final String code) {
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권 입니다."));

        voucherEntity.disable();
    }

    // 상품권 사용
    @Transactional
    public void useV2(final RequestContext requestContext,final String code) {
        final VoucherEntity voucherEntity = voucherRepository.findByCode(code)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품권 입니다."));

        voucherEntity.use();
    }
}
