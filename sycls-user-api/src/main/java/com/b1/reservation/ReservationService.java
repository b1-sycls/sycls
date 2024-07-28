package com.b1.reservation;

import com.b1.reservation.dto.ReservationGetRequestDto;
import com.b1.reservation.dto.ReservationGetResponseDto;
import com.b1.reservation.dto.ReservationReleaseRequestDto;
import com.b1.reservation.dto.ReservationReserveRequestDto;
import com.b1.reservation.dto.ReservationReserveResponseDto;
import com.b1.round.RoundHelper;
import com.b1.round.entity.Round;
import com.b1.seat.SeatReservationLogHelper;
import com.b1.seatgrade.entity.SeatGradeReservationLog;
import com.b1.seatgrade.SeatGradeHelper;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.user.entity.User;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Reservation Service")
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final RoundHelper roundHelper;
    private final SeatGradeHelper seatGradeHelper;
    private final SeatReservationLogHelper seatReservationLogHelper;

    /**
     * 예매 등록
     */
    public ReservationReserveResponseDto reserveReservation(
            final ReservationReserveRequestDto reservationRequest,
            final User user
    ) {
        Round selectedRound = roundHelper.getRound(reservationRequest.roundId());

        Set<SeatGrade> seatGradesForRound = seatGradeHelper
                .getAllSeatGradeByContentAndSeatGradeIds(
                        selectedRound, reservationRequest.seatGradeIds());

        Set<SeatGradeReservationLog> existingSeatGradeReservationLogs = seatReservationLogHelper
                .getSeatReservationLogsBySeatGrade(seatGradesForRound);

        boolean processReservation = seatReservationLogHelper.isProcessReservation(
                existingSeatGradeReservationLogs, user,
                reservationRequest.seatGradeIds());

        if (processReservation) {
            Set<SeatGradeReservationLog> newReservationLogs = new HashSet<>();
            for (SeatGrade seatGrade : seatGradesForRound) {
                newReservationLogs.add(SeatGradeReservationLog.addSeatReservationLog(seatGrade, user));
            }

            seatReservationLogHelper.addAllSeatReservationLogs(newReservationLogs);
        }
        return ReservationReserveResponseDto.of(selectedRound.getId(), seatGradesForRound);
    }

    /**
     * 예매 조회
     */
    @Transactional(readOnly = true)
    public ReservationGetResponseDto getReservation(
            final ReservationGetRequestDto requestDto,
            final User user
    ) {
        Round selectedRound = roundHelper.getRound(requestDto.roundId());

        Set<SeatGradeReservationLog> findSeatGradeReservationLogs = seatReservationLogHelper
                .getSeatReservationLogsByUser(user);

        return ReservationGetResponseDto.of(selectedRound, findSeatGradeReservationLogs);
    }

    /**
     * 예매 취소
     */
    public void releaseReservation(
            final ReservationReleaseRequestDto requestDto,
            final User user
    ) {
        Set<SeatGradeReservationLog> seatGradeReservationLogByUser = seatReservationLogHelper
                .getSeatReservationLogByUser(requestDto.reservationIds(), user);

        seatReservationLogHelper.deleteReservationLog(seatGradeReservationLogByUser);
    }

}
