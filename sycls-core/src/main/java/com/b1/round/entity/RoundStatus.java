package com.b1.round.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoundStatus {
    WAITING("WAITING"),    // 대기
    AVAILABLE("AVAILABLE"),  // 예매 가능
    CLOSED("CLOSED"),  // 마감
    ;

    private final String value;
}
