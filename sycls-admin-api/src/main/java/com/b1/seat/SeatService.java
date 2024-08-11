package com.b1.seat;

import com.b1.place.PlaceHelper;
import com.b1.place.entity.Place;
import com.b1.place.entity.PlaceStatus;
import com.b1.round.RoundHelper;
import com.b1.seat.dto.SeatAddRequestDto;
import com.b1.seat.dto.SeatGetAllResponseDto;
import com.b1.seat.dto.SeatGetResponseDto;
import com.b1.seat.dto.SeatUpdateRequestDto;
import com.b1.seat.entity.Seat;
import com.b1.seat.entity.SeatStatus;
import java.util.Set;
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
    private final RoundHelper roundHelper;
    private final SeatHelper seatHelper;

    /**
     * 해당 공연장의 좌석 등록
     */
    public void addSeats(final Long placeId, final SeatAddRequestDto requestDto) {
        Place place = placeHelper.getPlace(placeId);

        // 공연장 최대 좌석수와 총좌석수 비교 예외처리
        seatHelper.checkMaxSeatAndSeatCount(placeId, place.getMaxSeat());

        // 좌석 등록 시 중복되는 좌석코드가 있는지 확인(해당 공연장에)
        seatHelper.checkForAddSeat(placeId, requestDto.code());

        Seat seat = Seat.addSeat(requestDto.code(), place);
        seatHelper.saveSeats(seat);
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

        // 해당 공연장을 사용하는 회차가 예매 진행중인지 확인
        roundHelper.existsRoundByPlaceIdAndStatus(requestDto.placeId());

        // 수정 시 중복되는 좌석코드가 존재하는지 확인(해당 공연장에)
        seatHelper.checkForUpdateSeat(requestDto.placeId(), requestDto.code());

        seat.updateSeat(requestDto.code());
        return seat.getId();
    }

    /**
     * 좌석 삭제
     */
    public void deleteSeat(final Long placeId, final Long seatId) {
        Seat seat = seatHelper.getSeat(seatId);
        Place place = seat.getPlace();

        // 좌석이 이미 삭제된 상태인지 확인
        SeatStatus.checkDeleted(seat.getStatus());

        // 해당 공연장을 사용하는 회차가 예매 진행중인지 확인
        roundHelper.existsRoundByPlaceIdAndStatus(placeId);

        seat.deleteSeat();

        // 최대 좌석수와 총 좌석수를 비교하고 공연장 상태 수정
        if (placeHelper.checkMaxSeatAndSeatCountForSeatDelete(place.getId(), place.getMaxSeat())) {
            place.updatePlaceStatus(PlaceStatus.INACTIVATED);
        }
    }
}
