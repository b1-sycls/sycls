package com.b1.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("ACTIVE"),
    DELETED("DELETED"),
    ;

    private final String value;

    public static boolean isDeleted(UserStatus status) {
        return DELETED.equals(status);
    }
}
