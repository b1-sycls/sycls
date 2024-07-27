package com.b1.seat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Seat Reservation Log Status")
@Getter
@RequiredArgsConstructor
public enum SeatReservationLogStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;

    private final String value;

}
