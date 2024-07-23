package com.b1.seat;

import com.b1.place.PlaceAdapter;
import com.b1.place.entity.Place;
import com.b1.seat.dto.SeatAddRequestDto;
import com.b1.seat.dto.SeatGetAllResponseDto;
import com.b1.seat.entity.Seat;
import java.util.Set;
import java.util.stream.Collectors;
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
     * 해당 공연장의 좌석 등록
     */
    public void addSeats(final Long placeId, final SeatAddRequestDto requestDto) {
        Place place = placeAdapter.getPlace(placeId);

        Set<Seat> seatSet = requestDto.codeList().stream()
                .map(code -> Seat.addSeat(code, place))
                .collect(Collectors.toSet());
        seatAdapter.saveSeats(seatSet);
    }

    /**
     * 해당 공연장의 좌석 전체 조회
     */
    @Transactional(readOnly = true)
    public SeatGetAllResponseDto getAllSeats(final Long placeId) {
        placeAdapter.existPlace(placeId);

        Set<Seat> seatSet = seatAdapter.getAllSeats(placeId);
        return SeatGetAllResponseDto.of(seatSet);
    }
}
