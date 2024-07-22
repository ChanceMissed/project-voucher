package com.example.project_voucher.app.controller.voucher.response;


public record VoucherUseV2Response(
    String orderId // 성공을 했다. 승인번호만 내려주면된다
    ) {
}
