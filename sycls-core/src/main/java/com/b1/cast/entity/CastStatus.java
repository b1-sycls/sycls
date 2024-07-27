package com.b1.cast.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Cast Status")
@Getter
@RequiredArgsConstructor
public enum CastStatus {

    SCHEDULED("SCHEDULED"), // 출연 예정
    CANCELLED("CANCELLED"), // 취소
    ;

    private final String value;
}
