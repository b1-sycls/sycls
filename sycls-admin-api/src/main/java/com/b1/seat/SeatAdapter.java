package com.b1.seat;

import com.b1.seat.entity.Seat;
import java.util.Set;
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
    public void saveSeats(final Set<Seat> seatSet) {
        seatRepository.saveAll(seatSet);
    }

    /**
     * 해당 공연장의 좌석 전체 조회
     */
    public Set<Seat> getAllSeats(final Long placeId) {
        return seatRepository.findByPlaceId(placeId);
    }
}
