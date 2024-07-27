package com.b1.seatgrade.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatGradeType {

    VIP("VIP"),
    R("ROYAL"),
    S("SUPERIOR"),
    A("A_GRADE"),
    ;

    private final String value;
}
