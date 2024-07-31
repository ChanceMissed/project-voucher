package com.example.project_voucher.domain.service.validator;

import com.example.project_voucher.common.dto.RequestContext;
import com.example.project_voucher.storage.voucher.ContractEntity;
import com.example.project_voucher.storage.voucher.VoucherEntity;
import org.springframework.stereotype.Component;

@Component
public class VoucherDisableValidator {

    public void validate(VoucherEntity voucherEntity, RequestContext requestContext) {
        // 발행 이력을 검증 - 발행 타입과 아이디가 같아야한다.
        if (voucherEntity.publishHistory().getRequesterType() != requestContext.requesterType()
            || !voucherEntity.publishHistory().getRequestId().equals(requestContext.requesterId())) {
            throw new IllegalArgumentException("사용 불가 처리 권한이 없는 상품권 입니다.");
        }
    }
}
