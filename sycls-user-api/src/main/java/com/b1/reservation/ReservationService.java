package com.b1.reservation;

import com.b1.reservation.dto.ReservationAddRequestDto;
import com.b1.reservation.dto.ReservationAddResponseDto;
import com.b1.reservation.dto.ReservationGetDetailResponseDto;
import com.b1.reservation.dto.ReservationGetOccupiedResponseDto;
import com.b1.reservation.dto.ReservationGetResponseDto;
import com.b1.round.RoundHelper;
import com.b1.round.entity.Round;
import com.b1.seatgrade.SeatGradeHelper;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeReservationLog;
import com.b1.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j(topic = "Reservation Service")
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final RoundHelper roundHelper;
    private final SeatGradeHelper seatGradeHelper;

    private final ReservationHelper reservationHelper;

    /**
     * 예매 등록
     */
    public ReservationAddResponseDto addReservation(ReservationAddRequestDto requestDto, User user) {
        Round selectedRound = roundHelper.getRound(requestDto.roundId());

        Set<SeatGrade> seatGradesForRound = seatGradeHelper
                .getAllSeatGradeByRoundAndSeatGradeIds(
                        selectedRound, requestDto.seatGradeIds());
        Set<Long> seatGradeIds = seatGradesForRound
                .stream()
                .map(SeatGrade::getId)
                .collect(Collectors.toSet());
        reservationHelper.addReservation(requestDto.roundId(), seatGradeIds, user.getId());

        return ReservationAddResponseDto.of(selectedRound.getId(), seatGradesForRound);
    }

    /**
     * 예매 조회
     */
    @Transactional(readOnly = true)
    public ReservationGetResponseDto getReservation(
            final Long roundId,
            final User user
    ) {
        Round selectedRound = roundHelper.getRound(roundId);

        Set<Long> seatReservationIds = reservationHelper
                .getReservationByUser(selectedRound.getId(), user.getId());

        Set<SeatGrade> seatGradesForRound = seatGradeHelper
                .getAllSeatGradeByRoundAndSeatGradeIds(
                        selectedRound, seatReservationIds);

        return ReservationGetResponseDto.of(selectedRound, seatGradesForRound);
    }

    /**
     * 예매 상세 조회
     */
    @Transactional(readOnly = true)
    public ReservationGetDetailResponseDto getReservationDetail(
            final Long roundId,
            final User user
    ) {
        Map<String, List<SeatGrade>> map = reservationHelper
                .getReservationDetailByUser(roundId, user.getId());

        return ReservationGetDetailResponseDto.of(map);
    }

    /**
     * 예매 취소
     */
    public void releaseReservation(
            final Long roundId,
            final User user
    ) {
        Round selectedRound = roundHelper.getRound(roundId);

        reservationHelper.releaseReservation(selectedRound.getId(), user.getId());
    }

    /**
     * 점유 중인 좌석 조회
     */
    @Transactional(readOnly = true)
    public ReservationGetOccupiedResponseDto getOccupied(
            final Long roundId
    ) {
        Round selectedRound = roundHelper.getRound(roundId);

        Set<Long> seatOccupiedIds = reservationHelper.getOccupied(selectedRound.getId());

        return ReservationGetOccupiedResponseDto.of(selectedRound.getId(), seatOccupiedIds);
    }

}
