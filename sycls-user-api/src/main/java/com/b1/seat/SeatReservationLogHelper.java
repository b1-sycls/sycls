package com.b1.seat;

import static com.b1.constant.DomainConstant.*;

import com.b1.exception.customexception.SeatReservationLogNotAvailableException;
import com.b1.exception.errorcode.SeatReservationLogErrorCode;
import com.b1.seat.entity.SeatGrade;
import com.b1.seat.entity.SeatReservationLog;
import java.time.LocalDateTime;
import java.util.Set;
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
        Set<SeatReservationLog> seatReservationLogs = seatReservationLogRepository
                .findAllBySeatGradeInOrderByStartTimeDesc(seatGrades);

        seatReservationLogs.forEach(seatReservationLog -> {
            if (seatReservationLog.getStartTime().plusMinutes(SEAT_RESERVATION_TIME)
                    .isAfter(LocalDateTime.now())) {
                throw new SeatReservationLogNotAvailableException(
                        SeatReservationLogErrorCode.SEAT_RESERVATION_NOT_AVAILABLE);
            }
        });
    }

    public void addAllSeatReservationLogs(Set<SeatReservationLog> createSeatReservationLogs) {
        seatReservationLogRepository.saveAll(createSeatReservationLogs);
    }
}
