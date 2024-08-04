package com.b1.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

public record PaymentSuccessRequestDto(
        @NotBlank(message = "주문번호가 누락되었습니다.")
        String orderId,
        @NotNull(message = "결제금액이 누락되었습니다.")
        Integer price,
        @NotNull(message = "좌석 예매 번호가 누락되었습니다.")
        List<Long> seatGradeIds
) {
}
