package com.b1.reservation;

import com.b1.reservation.dto.ReservationReleaseRequestDto;
import com.b1.reservation.dto.ReservationReleaseResponseDto;
import com.b1.reservation.dto.ReservationReserveRequestDto;
import com.b1.reservation.dto.ReservationReserveResponseDto;
import com.b1.round.RoundHelper;
import com.b1.round.entity.Round;
import com.b1.seat.SeatGradeHelper;
import com.b1.seat.SeatReservationLogHelper;
import com.b1.seat.entity.SeatReservationLog;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.user.entity.User;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
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

        Set<SeatReservationLog> existingSeatReservationLogs = seatReservationLogHelper
                .getSeatReservationLogs(seatGradesForRound);

        boolean processReservation = seatReservationLogHelper.isProcessReservation(
                existingSeatReservationLogs, user,
                reservationRequest.seatGradeIds());

        if (processReservation) {
            Set<SeatReservationLog> newReservationLogs = new HashSet<>();
            for (SeatGrade seatGrade : seatGradesForRound) {
                newReservationLogs.add(SeatReservationLog.addSeatReservationLog(seatGrade, user));
            }

            seatReservationLogHelper.addAllSeatReservationLogs(newReservationLogs);
        }
        return ReservationReserveResponseDto.of(selectedRound.getId(), seatGradesForRound);
    }

}
