package com.example.project_voucher.app.controller.voucher.request;

import com.example.project_voucher.common.type.RequesterType;
import com.example.project_voucher.common.type.VoucherAmountType;

public record VoucherUseV2Request(
    RequesterType requesterType, // 제휴사
    String requesterId, // 단순 서버에서 기록용으로 / 파트너 사마다 다르게 올 수 있음
    String code) {
}
