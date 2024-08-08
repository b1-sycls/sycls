package com.b1.reservation.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@RedisHash("reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Reservation implements Serializable {

    @Id
    private String key;

    @TimeToLive
    private long ttl;

    public static Reservation of(String key, long ttl) {
        return Reservation.builder()
                .key(key)
                .ttl(ttl)
                .build();
    }
}