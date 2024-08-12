package com.b1.seatgrade;

import com.b1.exception.customexception.SeatGradeDuplicatedException;
import com.b1.exception.customexception.SeatGradeNotFoundException;
import com.b1.exception.customexception.SeatNotFoundException;
import com.b1.exception.errorcode.SeatErrorCode;
import com.b1.exception.errorcode.SeatGradeErrorCode;
import com.b1.place.PlaceQueryRepository;
import com.b1.round.dto.RoundSeatGradeStatusDto;
import com.b1.seat.SeatRepository;
import com.b1.seat.entity.Seat;
import com.b1.seatgrade.dto.SeatGradeAdminGetResponseDto;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeStatus;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Seat Grade Helper")
@Component
@RequiredArgsConstructor
public class SeatGradeHelper {

    private final PlaceQueryRepository placeQueryRepository;
    private final SeatRepository seatRepository;
    private final SeatGradeRepository seatGradeRepository;
    private final SeatGradeQueryRepository seatGradeQueryRepository;

    /**
     * 중복확인을 위한 SeatGrade roundId로 조회
     */
    public void checkAllSeatGradesByRoundIdAndSeatIdIn(
            final Long roundId,
            final List<Long> seatIdList
    ) {
        if (seatGradeRepository.existsByRoundIdAndSeatIdInAndStatus(
                roundId,
                seatIdList,
                SeatGradeStatus.ENABLE)
        ) {
            log.error("중복되는 좌석 | {}", seatIdList.toString());
            throw new SeatGradeDuplicatedException(SeatGradeErrorCode.DUPLICATED_SEAT_GRADE);
        }

    }

    /**
     * 좌석 등급 등록을 위한 좌석 조회
     */
    public List<Seat> getSeatForAddSeatGrade(final List<Long> seatIdList) {
        return seatRepository.findAllByIdIn(seatIdList).orElseThrow(
                () -> {
                    log.error("찾을 수 없는 좌석 | {}", seatIdList.toString());
                    return new SeatNotFoundException(SeatErrorCode.NOT_FOUND_SEAT);
                }
        );
    }

    /**
     * 좌석-등급 등록
     */
    public void saveSeatGrades(final List<SeatGrade> seatGradeList) {
        seatGradeRepository.saveAll(seatGradeList);
    }

    /**
     * 최대좌석수와 총 좌석수 비교
     */
    public Boolean checkMaxSeatsAndSeatCount(final Long placeId, final Integer maxSeat) {
        Long seatCount = placeQueryRepository.getSeatCount(placeId);
        return seatCount != maxSeat.longValue();
    }

    /**
     * 좌석 등급 전체 조회
     */
    public List<SeatGradeAdminGetResponseDto> getAllSeatGrades(final Long roundId) {
        return seatGradeQueryRepository.getAllSeatGrades(roundId);
    }

    /**
     * 좌석-등급 ID In절 조회
     */
    public List<SeatGrade> findAllByIdIn(final Set<Long> seatGradeIdList) {
        List<SeatGrade> seatGradeList = seatGradeRepository.findAllByIdIn(seatGradeIdList);
        if (seatGradeList.isEmpty()) {
            log.error("찾을 수 없는 좌석등급정보 | {}", seatGradeIdList.toString());
            throw new SeatGradeNotFoundException(SeatGradeErrorCode.NOT_FOUND_SEAT_GRADE);
        }
        return seatGradeList;
    }

    /**
     * 해당 회차의 공연장의 최대좌석수와 enable 된 seatGrade 의 수를 반환
     */
    public RoundSeatGradeStatusDto getPlaceMaxSeatAndEnableSeatGradeByRoundId(final Long roundId) {
        return seatGradeQueryRepository.getPlaceMaxSeatAndEnableSeatGradeByRoundId(roundId);
    }

    /**
     * 해당 회차의 모든 좌석등급을 불러옴
     */
    public List<SeatGrade> getAllSeatGradesEntity(final Long roundId) {
        return seatGradeRepository.findAllByRoundId(roundId);
    }
}
