package com.b1.reservation;

import com.b1.reservation.dto.ReservationGetDetailResponseDto;
import com.b1.reservation.dto.ReservationGetOccupiedResponseDto;
import com.b1.reservation.dto.ReservationGetResponseDto;
import com.b1.reservation.dto.ReservationReleaseRequestDto;
import com.b1.reservation.dto.ReservationReserveRequestDto;
import com.b1.reservation.dto.ReservationReserveResponseDto;
import com.b1.round.RoundHelper;
import com.b1.round.entity.Round;
import com.b1.seatgrade.SeatGradeHelper;
import com.b1.seatgrade.SeatGradeReservationLogHelper;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeReservationLog;
import com.b1.user.entity.User;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    private final SeatGradeReservationLogHelper seatGradeReservationLogHelper;

    /**
     * 예매 등록
     */
    public ReservationReserveResponseDto reserveReservation(
            final ReservationReserveRequestDto reservationRequest,
            final User user
    ) {
        Round selectedRound = roundHelper.getRound(reservationRequest.roundId());

        Set<SeatGrade> seatGradesForRound = seatGradeHelper
                .getAllSeatGradeByRoundAndSeatGradeIds(
                        selectedRound, reservationRequest.seatGradeIds());

        Set<SeatGradeReservationLog> existingSeatGradeReservationLogs = seatGradeReservationLogHelper
                .getSeatReservationLogsBySeatGrade(seatGradesForRound);

        boolean processReservation = seatGradeReservationLogHelper.isProcessReservation(
                existingSeatGradeReservationLogs, user,
                reservationRequest.seatGradeIds());

        if (processReservation) {
            Set<SeatGradeReservationLog> newReservationLogs = new HashSet<>();
            for (SeatGrade seatGrade : seatGradesForRound) {
                newReservationLogs.add(
                        SeatGradeReservationLog.addSeatReservationLog(seatGrade, user));
            }

            seatGradeReservationLogHelper.addAllSeatReservationLogs(newReservationLogs);
        }
        return ReservationReserveResponseDto.of(selectedRound.getId(), seatGradesForRound);
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

        Set<SeatGradeReservationLog> findSeatGradeReservationLogs = seatGradeReservationLogHelper
                .getSeatReservationLogsByUser(user);

        return ReservationGetResponseDto.of(selectedRound, findSeatGradeReservationLogs);
    }

    /**
     * 예매 상세 조회
     */
    @Transactional(readOnly = true)
    public ReservationGetDetailResponseDto getReservationDetail(
            final User user
    ) {
        Map<String, List<SeatGradeReservationLog>> map = seatGradeReservationLogHelper.
                getSeatReservationLogsBySeatGrade(user);

        return ReservationGetDetailResponseDto.of(map);
    }

    /**
     * 예매 취소
     */
    public void releaseReservation(
            final ReservationReleaseRequestDto requestDto,
            final User user
    ) {
        Set<SeatGradeReservationLog> seatGradeReservationLogByUser = seatGradeReservationLogHelper
                .getSeatReservationLogByUser(requestDto.reservationIds(), user);

        for (SeatGradeReservationLog seatGradeReservationLog : seatGradeReservationLogByUser) {
            seatGradeReservationLog.deleteReservationStatus();
        }
    }

    /**
     * 점유 중인 좌석 조회
     */
    @Transactional(readOnly = true)
    public ReservationGetOccupiedResponseDto getOccupied(
            final Long roundId
    ) {
        Round selectedRound = roundHelper.getRound(roundId);

        Set<SeatGrade> seatGradesForRound = seatGradeHelper.getAllSeatGradesByRound(selectedRound);

        Set<SeatGradeReservationLog> existingSeatGradeReservationLogs = seatGradeReservationLogHelper
                .getSeatReservationLogsBySeatGrade(seatGradesForRound);

        return ReservationGetOccupiedResponseDto.of(
                selectedRound,
                existingSeatGradeReservationLogs
        );
    }

}
