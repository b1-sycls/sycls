package com.b1.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponseDto {

    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;

    public static UserProfileResponseDto of(String username, String nickname, String email,
            String phoneNumber) {
        return UserProfileResponseDto.builder()
                .username(username)
                .nickname(nickname)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
}
