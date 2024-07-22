package com.example.project_voucher.app.controller.voucher.request;

import com.example.project_voucher.common.type.RequesterType;
import com.example.project_voucher.common.type.VoucherAmountType;

public record VoucherPublishV2Request(
    RequesterType requesterType, // 제휴사
    String requesterId, // 쇼핑몰 주문번호
    VoucherAmountType amountType) {
}
