package com.b1.seat;

import com.b1.place.PlaceAdapter;
import com.b1.place.dto.SeatUpdateRequestDto;
import com.b1.place.entity.Place;
import com.b1.seat.dto.SeatAddRequestDto;
import com.b1.seat.dto.SeatGetAllResponseDto;
import com.b1.seat.dto.SeatGetResponseDto;
import com.b1.seat.entity.Seat;
import com.b1.seat.entity.SeatStatus;
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

    /**
     * 좌석의 상세정보(단건) 조회
     */
    @Transactional(readOnly = true)
    public SeatGetResponseDto getSeat(final Long seatId) {
        Seat seat = seatAdapter.getSeat(seatId);
        return SeatGetResponseDto.of(seat);
    }

    /**
     * 좌석 수정
     */
    public Long updateSeat(final Long seatId, final SeatUpdateRequestDto requestDto) {
        Seat seat = seatAdapter.getSeat(seatId);
        seat.updateSeat(requestDto.code(), requestDto.status());
        return seat.getId();
    }

    /**
     * 좌석 삭제
     */
    public void deleteSeat(final Long seatId) {
        Seat seat = seatAdapter.getSeat(seatId);
        SeatStatus.checkDeleted(seat.getStatus());
        seat.deleteSeat();
    }
}
