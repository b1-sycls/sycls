package com.b1.auth.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@RedisHash("Code")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Code implements Serializable {

    @Id
    private String email;

    private String code;

    @TimeToLive
    private long ttl;

}