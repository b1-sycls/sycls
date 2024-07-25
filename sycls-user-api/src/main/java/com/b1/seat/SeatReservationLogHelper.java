package com.b1.seat;

import static com.b1.constant.DomainConstant.SEAT_RESERVATION_TIME;

import com.b1.exception.customexception.SeatReservationLogNotAvailableException;
import com.b1.exception.errorcode.SeatReservationLogErrorCode;
import com.b1.seat.entity.SeatReservationLog;
import com.b1.seatgrade.entity.SeatGrade;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Seat Reservation Log Helper")
@Component
@RequiredArgsConstructor
public class SeatReservationLogHelper {

    private final SeatReservationLogRepository seatReservationLogRepository;

    /**
     * 특정 공연장 좌석 등급 점유 조회
     *
     * @throws SeatReservationLogNotAvailableException 이미 매진 된 좌석 또는 점유 중인 좌석
     */
    public void getAllSeatReservationLogs(Set<SeatGrade> seatGrades) {
        List<Long> seatGradeIds = seatGrades.stream()
                .map(SeatGrade::getId)
                .collect(Collectors.toList());

        Set<SeatReservationLog> seatReservationLogs = seatReservationLogRepository
                .findAllBySeatGradeInOrderByStartTimeDesc(seatGrades);

        if (!seatReservationLogs.isEmpty()) {
            LocalDateTime now = LocalDateTime.now();
            boolean anyOccupied = seatReservationLogs.stream()
                    .anyMatch(log -> log.getStartTime().plusMinutes(SEAT_RESERVATION_TIME)
                            .isAfter(now));

            if (anyOccupied) {
                log.error("점유 중 좌석 등급 | request {}", seatGradeIds);
                throw new SeatReservationLogNotAvailableException(
                        SeatReservationLogErrorCode.SEAT_RESERVATION_NOT_AVAILABLE);
            }
        }
    }


    public void addAllSeatReservationLogs(Set<SeatReservationLog> createSeatReservationLogs) {
        seatReservationLogRepository.saveAll(createSeatReservationLogs);
    }
}
