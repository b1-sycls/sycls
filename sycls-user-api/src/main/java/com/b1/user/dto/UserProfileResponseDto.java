package com.b1.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
