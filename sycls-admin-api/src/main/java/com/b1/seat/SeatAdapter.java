package com.b1.seat;

import com.b1.seat.entity.Seat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Seat Adapter")
@Component
@RequiredArgsConstructor
public class SeatAdapter {

    private final SeatRepository seatRepository;

    /**
     * 좌석 등록
     */
    public void saveSeats(final List<Seat> seatList) {
        seatRepository.saveAll(seatList);
    }
}
