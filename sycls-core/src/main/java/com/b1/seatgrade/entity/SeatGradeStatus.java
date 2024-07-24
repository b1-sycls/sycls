package com.b1.seatgrade.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatGradeStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;
    private final String value;
}
