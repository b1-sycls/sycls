package com.b1.seat;

import com.b1.place.PlaceAdapter;
import com.b1.place.entity.Place;
import com.b1.seat.dto.SeatAddRequestDto;
import com.b1.seat.entity.Seat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Seat Service")
@Service
@RequiredArgsConstructor
@Transactional
public class SeatService {

    private final PlaceAdapter placeAdapter;
    private final SeatAdapter seatAdapter;

    /**
     * 좌석 등록
     */
    public void addSeats(final Long placeId, final SeatAddRequestDto requestDto) {
        Place place = placeAdapter.getPlace(placeId);

        List<Seat> seatList = requestDto.codeList().stream()
                .map(code -> Seat.addSeat(code, place))
                .toList();
        seatAdapter.saveSeats(seatList);
    }
}
