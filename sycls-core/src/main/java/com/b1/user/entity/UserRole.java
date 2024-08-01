package com.b1.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN"),
    ;

    private final String authority;

    public static boolean isAdmin(UserRole role) {
        return ADMIN.equals(role);
    }
}
