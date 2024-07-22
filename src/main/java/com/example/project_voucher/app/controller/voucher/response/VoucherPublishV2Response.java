package com.example.project_voucher.app.controller.voucher.response;


public record VoucherPublishV2Response(
    String orderId, // 승인번호 대신 주문번호로 대체
    String code) {
}
