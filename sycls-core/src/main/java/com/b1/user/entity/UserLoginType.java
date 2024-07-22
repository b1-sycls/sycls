package com.b1.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserLoginType {
    KAKAO("KAKAO"),
    COMMON("COMMON"),
    ;

    private final String value;
}
