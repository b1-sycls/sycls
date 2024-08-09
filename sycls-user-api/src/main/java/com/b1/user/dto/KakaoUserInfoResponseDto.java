package com.b1.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoUserInfoResponseDto {

    private Long id;
    private String nickname;
    private String email;
    @Setter
    private boolean newUser;

    public static KakaoUserInfoResponseDto of(final Long id, final String nickname,
            final String email) {
        return KakaoUserInfoResponseDto.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .build();
    }
}