package com.b1.seat;

import com.b1.exception.customexception.SeatNotFoundException;
import com.b1.exception.errorcode.SeatErrorCode;
import com.b1.seat.entity.Seat;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Seat Helper")
@Component
@RequiredArgsConstructor
public class SeatHelper {

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

    /**
     * 좌석 단건조회 (좌석등급 조회할때도 사용)
     */
    public Seat getSeat(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(
                () -> {
                    log.error("찾을 수 없는 좌석 | {}", seatId);
                    return new SeatNotFoundException(SeatErrorCode.NOT_FOUND_SEAT);
                }
        );
    }

    public Set<Seat> getAllSeatByPlaceId(Long placeId) {
        return seatRepository.findByPlaceId(placeId);
    }
}
