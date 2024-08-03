package com.b1.payment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record TossConfirmRequestDto(
        @NotBlank(message = "필수 값이 누락되었습니다.")
        String orderId,
        @NotBlank(message = "필수 값이 누락되었습니다.")
        String amount,
        @NotBlank(message = "필수 값이 누락되었습니다.")
        String paymentKey
) {
}
