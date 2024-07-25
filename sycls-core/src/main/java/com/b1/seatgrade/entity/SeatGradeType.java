package com.b1.seatgrade.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatGradeType {

    VIP("VIP"),
    ROYAL("ROYAL"),
    SUPERIOR("SUPERIOR"),
    A("A"),
    ;

    private final String value;
}
