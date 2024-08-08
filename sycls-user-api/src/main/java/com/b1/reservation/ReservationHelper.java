package com.b1.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ReservationHelper {
    private final ReservationRepository reservationRepository;

    /**
     * 선택 좌석 점유 등록
     */
    public void addReservation(
            final Long roundId,
            final Set<Long> seatGradeIds,
            final Long userId
    ) {
        reservationRepository.reserveSeats(roundId, seatGradeIds, userId);
    }

}
