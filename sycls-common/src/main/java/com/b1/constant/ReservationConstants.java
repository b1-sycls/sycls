package com.b1.constant;

public abstract class ReservationConstants {

    public static final int MAXIMUM_SELECTED_SEAT = 4; //최대 좌석 수
    public static final long LOCK_EXPIRATION_TIME = 10; // 10초 동안 잠금 유지
    public static final long RESERVATION_EXPIRATION_TIME = 5; // 예약 만료 시간 (분)
    public static final String REDISSON_LOCK_KEY_PREFIX = "reservation:";
}
