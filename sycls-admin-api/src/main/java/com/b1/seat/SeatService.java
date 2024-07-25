package com.b1.seat;

import com.b1.place.PlaceHelper;
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

    private final PlaceHelper placeHelper;
    private final SeatHelper seatHelper;

    /**
     * 해당 공연장의 좌석 등록
     */
    public void addSeats(final Long placeId, final SeatAddRequestDto requestDto) {
        Place place = placeHelper.getPlace(placeId);

        Set<Seat> seatSet = requestDto.codeList().stream()
                .map(code -> Seat.addSeat(code, place))
                .collect(Collectors.toSet());
        seatHelper.saveSeats(seatSet);
    }

    /**
     * 해당 공연장의 좌석 전체 조회
     */
    @Transactional(readOnly = true)
    public SeatGetAllResponseDto getAllSeats(final Long placeId) {
        placeHelper.existPlace(placeId);

        Set<Seat> seatSet = seatHelper.getAllSeats(placeId);
        return SeatGetAllResponseDto.of(seatSet);
    }

    /**
     * 좌석의 상세정보(단건) 조회
     */
    @Transactional(readOnly = true)
    public SeatGetResponseDto getSeat(final Long seatId) {
        Seat seat = seatHelper.getSeat(seatId);
        return SeatGetResponseDto.of(seat);
    }

    /**
     * 좌석 수정
     */
    public Long updateSeat(final Long seatId, final SeatUpdateRequestDto requestDto) {
        Seat seat = seatHelper.getSeat(seatId);
        seat.updateSeat(requestDto.code(), requestDto.status());
        return seat.getId();
    }

    /**
     * 좌석 삭제
     */
    public void deleteSeat(final Long seatId) {
        Seat seat = seatHelper.getSeat(seatId);
        SeatStatus.checkDeleted(seat.getStatus());
        seat.deleteSeat();
    }
}
