package com.b1.payment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientResponseDto {

    private String clientKey;

    public static ClientResponseDto of(
            final String paymentClientKey
    ) {
        return ClientResponseDto.builder()
                .clientKey(paymentClientKey)
                .build();
    }
}
