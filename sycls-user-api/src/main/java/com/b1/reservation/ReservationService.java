package com.b1.reservation;

import com.b1.reservation.dto.ReservationAddRequestDto;
import com.b1.reservation.dto.ReservationAddResponseDto;
import com.b1.reservation.dto.ReservationGetDetailResponseDto;
import com.b1.reservation.dto.ReservationGetOccupiedResponseDto;
import com.b1.reservation.dto.ReservationGetResponseDto;
import com.b1.reservation.dto.ReservationReleaseRequestDto;
import com.b1.round.RoundHelper;
import com.b1.round.entity.Round;
import com.b1.seatgrade.SeatGradeHelper;
import com.b1.seatgrade.SeatGradeReservationLogHelper;
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
    private final SeatGradeReservationLogHelper seatGradeReservationLogHelper;
    private static final long LOCK_EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes in milliseconds

    private final ReservationHelper reservationHelper;
    private final ReservationRepository reservationRepository;

    /**
     * 예매 등록
     */
    public ReservationAddResponseDto addReservation(ReservationAddRequestDto requestDto, User user) {
        Round selectedRound = roundHelper.getRound(requestDto.roundId());

        Set<SeatGrade> seatGradesForRound = seatGradeHelper
                .getAllSeatGradeByRoundAndSeatGradeIds(
                        selectedRound, requestDto.seatGradeIds());
        Set<Long> seatGradeIds = seatGradesForRound.stream().map(SeatGrade::getId).collect(Collectors.toSet());
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

    public List<String> getKeysByPattern(String pattern) {
//        return reservationHelper.getKeyByPattern(pattern);
        return null;
    }
}
