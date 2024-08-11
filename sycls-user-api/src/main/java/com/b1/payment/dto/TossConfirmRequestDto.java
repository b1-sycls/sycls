package com.b1.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public record TossConfirmRequestDto(
        @NotNull(message = "공연회차가 누락되었습니다.")
        Long roundId,
        @NotBlank(message = "주문번호가 누락되었습니다.")
        String orderId,
        @NotBlank(message = "가격이 누락되었습니다.")
        String amount,
        @NotBlank(message = "결제키가 누락되었습니다.")
        String paymentKey
) {
}
