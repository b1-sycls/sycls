package com.b1.sycls.domain.seat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;
    private final String value;

}
