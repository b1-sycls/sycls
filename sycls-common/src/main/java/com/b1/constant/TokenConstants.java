package com.b1.constant;

public abstract class TokenConstants {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESHTOKEN_HEADER = "RefreshToken";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final long TOKEN_TIME = 30 * 60 * 1000L; // 30분
    public static final long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L; // 2주
}
