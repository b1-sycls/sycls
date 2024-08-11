package com.b1.constant;

public class TokenConstants {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESHTOKEN_HEADER = "RefreshToken";
    public static final String BEARER_PREFIX = "Bearer ";
    // TODO accessToken ttl 60분으로 변경해뒀음. 배포 시에 30분으로 수정 해야함.
    public static final long TOKEN_TIME = 60 * 60 * 1000L; // 30분
    public static final long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L; // 2주
}
