package com.b1.seat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatAdapter {

    private final SeatRepository seatRepository;
}
