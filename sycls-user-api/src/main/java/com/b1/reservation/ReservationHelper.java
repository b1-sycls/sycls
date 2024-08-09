package com.b1.reservation;

import com.b1.seatgrade.SeatGradeRepository;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeReservationLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j(topic = "Reservation Helper")
@Component
@RequiredArgsConstructor
public class ReservationHelper {

    private final ReservationRepository reservationRepository;
    private final SeatGradeRepository seatGradeRepository;
    private final SeatReservationLogRepository seatReservationLogRepository;

    /**
     * 선택 좌석 점유 등록
     */
    public void addReservation(
            final Long roundId,
            final Set<Long> seatGradeIds,
            final Long userId
    ) {
        reservationRepository.reserveSeats(roundId, seatGradeIds, userId);
    }

    /**
     * 특정 유저 예매 좌석 조회
     */
    public Set<Long> getReservationByUser(
            final Long roundId,
            final Long userId
    ) {
        return reservationRepository.getReservationByUser(roundId, userId);
    }

    /**
     * 예매 좌석 취소
     */
    public void releaseReservation(
            final Long roundId,
            final Long userId
    ) {
        reservationRepository.releaseReservation(roundId, userId);
    }

    /**
     * 특정 유저 예매 좌석 상세 조회
     */
    public Map<String, List<SeatGrade>> getReservationDetailByUser(
            final Long roundId,
            final Long userId
    ) {
        Set<Long> seatReservationIds = reservationRepository.getReservationByUser(roundId, userId);
        List<SeatGrade> selectSeatGrades = seatGradeRepository.findAllByIdIn(seatReservationIds);

        return getLogSeatGradeMap(selectSeatGrades);
    }

    /**
     * 점유 중인 좌석 조회
     */
    public Set<Long> getOccupied(
            final Long roundId
    ) {
        return reservationRepository.getOccupied(roundId);
    }

    /**
     * 특정 예매 정보 조회
     */
    public List<SeatGradeReservationLog> getSeatReservationLogsById(
            final List<Long> reservationIds
    ) {
        return seatReservationLogRepository.findAllByIdIn(reservationIds);
    }

    /**
     * 좌석 등급별 데이터 추출
     */
    private Map<String, List<SeatGrade>> getLogSeatGradeMap(
            final List<SeatGrade> seatGrades
    ) {
        return seatGrades.stream()
                .collect(Collectors.groupingBy(
                        sg -> sg.getGrade().getValue()
                ));
    }

}
