package com.b1.seat;

import com.b1.exception.customexception.SeatCannotAddException;
import com.b1.exception.customexception.SeatCodeDuplicatedException;
import com.b1.exception.customexception.SeatNotFoundException;
import com.b1.exception.errorcode.SeatErrorCode;
import com.b1.place.PlaceQueryRepository;
import com.b1.seat.entity.Seat;
import com.b1.seat.entity.SeatStatus;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Seat Helper")
@Component
@RequiredArgsConstructor
public class SeatHelper {

    private final SeatRepository seatRepository;
    private final PlaceQueryRepository placeQueryRepository;

    /**
     * 좌석 등록
     */
    public void saveSeats(final Seat seat) {
        seatRepository.save(seat);
    }

    /**
     * 해당 공연장의 좌석 전체 조회
     */
    public Set<Seat> getAllSeats(final Long placeId) {
        return seatRepository.findAllByPlaceIdAndStatus(placeId, SeatStatus.ENABLE);
    }

    /**
     * 좌석 단건조회 (좌석등급 조회할때도 사용)
     */
    public Seat getSeat(final Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(
                () -> {
                    log.error("찾을 수 없는 좌석 | {}", seatId);
                    return new SeatNotFoundException(SeatErrorCode.NOT_FOUND_SEAT);
                }
        );
    }

    /**
     * 총 좌석수와 공연장 최대 좌석수가 같은지 확인 momoysm
     */
    public void checkMaxSeatAndSeatCount(final Long placeId, final Integer maxSeat) {
        Long seatCount = placeQueryRepository.getSeatCount(placeId);
        if (maxSeat.longValue() <= seatCount) {
            log.error("좌석 최대 등록 완료 | {}", placeId);
            throw new SeatCannotAddException(SeatErrorCode.CANNOT_ADD_SEAT);
        }
    }

    /**
     * 좌석 등록을 위한 예외처리
     */
    public void checkForAddSeat(final Long placeId, final String code) {
        seatRepository.findAllByPlaceIdAndStatus(placeId, SeatStatus.ENABLE)
                .stream()
                .map(Seat::getCode)
                .filter(code::contains)
                .findAny()
                .ifPresent(seatCode -> {
                    log.error("중복된 좌석코드 존재 | {}", seatCode);
                    throw new SeatCodeDuplicatedException(SeatErrorCode.DUPLICATED_SEAT);
                });
    }

    /**
     * 좌석 수정을 위한 에외처리
     */
    public void checkForUpdateSeat(final Long placeId, final String code) {
        seatRepository.findAllByPlaceIdAndStatus(placeId, SeatStatus.ENABLE)
                .stream()
                .filter(existSeat -> code.equals(existSeat.getCode()))
                .findAny()
                .ifPresent(existSeat -> {
                    log.error("수정 불가능한 좌석 코드 | {}, {}", placeId, code);
                    throw new SeatCodeDuplicatedException(SeatErrorCode.DUPLICATED_SEAT);
                });
    }
}
