package com.b1.payment.dto;

import com.b1.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientResponseDto {

    private String clientKey;
    private String username;
    private String email;
    private String phoneNumber;

    public static ClientResponseDto of(
            final String paymentClientKey,
            final User user
    ) {
        return ClientResponseDto.builder()
                .clientKey(paymentClientKey)
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
