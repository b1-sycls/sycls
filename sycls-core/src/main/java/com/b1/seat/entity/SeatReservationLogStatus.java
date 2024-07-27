package com.b1.seat.entity;

import com.b1.exception.customexception.SeatReservationAlreadyDisableException;
import com.b1.exception.errorcode.SeatReservationLogErrorCode;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Seat Reservation Log Status")
@Getter
@RequiredArgsConstructor
public enum SeatReservationLogStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;

    private final String value;

    public static void checkDisables(Set<SeatReservationLog> seatReservationLogByUser) {
        for (SeatReservationLog seatReservationLog : seatReservationLogByUser) {
            SeatReservationLogStatus status = seatReservationLog.getStatus();
            if (status.equals(DISABLE)) {
                log.error("이미 삭제 된 예매 정보 | request {} ", status);
                throw new SeatReservationAlreadyDisableException(
                        SeatReservationLogErrorCode.SEAT_RESERVATION_ALREADY_DISABLE);
            }
        }
    }
}
