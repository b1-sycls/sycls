package com.b1.auth.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@RedisHash("Token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token implements Serializable {

    @Id
    private String access;

    private String refresh;

    @TimeToLive
    private long ttl;
}