package com.b1.constant;

public abstract class RedissonConstants {
    public static final long LOCK_EXPIRATION_TIME = 10; // 10초 동안 잠금 유지
    public static final long RESERVATION_EXPIRATION_TIME = 5; // 만료 시간 (분)
    public static final String REDISSON_RESERVATION_LOCK_KEY_PREFIX = "reservation:";
    public static final String REDISSON_PAYMENT_LOCK_KEY_PREFIX = "payment:";
}
