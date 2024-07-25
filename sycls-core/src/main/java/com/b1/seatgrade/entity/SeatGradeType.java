package com.b1.seatgrade.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatGradeType {

    VIP("vip"),
    ROYAL("royal"),
    SUPERIOR("superior"),
    A_GRADE("a"),
    ;


    private final String value;
}