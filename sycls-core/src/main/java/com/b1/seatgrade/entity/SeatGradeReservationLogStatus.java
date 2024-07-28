package com.b1.seatgrade.entity;

import com.b1.exception.customexception.SeatReservationAlreadyDisableException;
import com.b1.exception.errorcode.SeatReservationLogErrorCode;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Seat Reservation Log Status")
@Getter
@RequiredArgsConstructor
public enum SeatGradeReservationLogStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;

    private final String value;

    public static void checkDisables(
            final Set<SeatGradeReservationLog> seatGradeReservationLogByUser
    ) {
        for (SeatGradeReservationLog seatGradeReservationLog : seatGradeReservationLogByUser) {
            SeatGradeReservationLogStatus status = seatGradeReservationLog.getStatus();
            if (status.equals(DISABLE)) {
                log.error("이미 삭제 된 예매 정보 | request {} ", status);
                throw new SeatReservationAlreadyDisableException(
                        SeatReservationLogErrorCode.SEAT_RESERVATION_ALREADY_DISABLE);
            }
        }
    }
}
