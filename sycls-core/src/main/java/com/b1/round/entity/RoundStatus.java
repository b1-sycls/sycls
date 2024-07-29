package com.b1.round.entity;

import com.b1.exception.customexception.BookingNotAvailableException;
import com.b1.exception.customexception.RoundStatusEqualsException;
import com.b1.exception.customexception.SeatGradeCannnotUpdateException;
import com.b1.exception.errorcode.RoundErrorCode;
import com.b1.exception.errorcode.SeatGradeErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Round Status")
@Getter
@RequiredArgsConstructor
public enum RoundStatus {
    WAITING("WAITING"), // 대기
    AVAILABLE("AVAILABLE"), // 예매 가능
    CLOSED("CLOSED"),  // 마감
    ;

    private final String value;

    public static void checkClosed(final RoundStatus status) {
        if (status.equals(RoundStatus.CLOSED)) {
            log.error("해당 회차가 이미 닫혀있는 상태 request : {}", status);
            throw new RoundStatusEqualsException(RoundErrorCode.STATUS_ALREADY_CLOSED);
        }
    }

    public static void unCheckAvailable(final RoundStatus status) {
        if (!status.equals(AVAILABLE)) {
            log.error("예매 불가 상태 | request {}", status);
            throw new BookingNotAvailableException(RoundErrorCode.BOOKING_NOT_AVAILABLE);
        }
    }

    public static void checkAvailable(RoundStatus status) {
        if (status.equals(AVAILABLE)) {
            log.error("회차 등급 수정 불가 상태 | {}", status);
            throw new SeatGradeCannnotUpdateException(SeatGradeErrorCode.CANNOT_UPDATE_SEAT_GRADE);
        }
    }

    public static void checkEqualsStatus(final RoundStatus firstStatus,
            final RoundStatus secondStatus) {
        if (firstStatus.equals(secondStatus)) {
            log.error("회차 스테이터스 동일 오류 | status {} : {}", firstStatus, secondStatus);
            throw new RoundStatusEqualsException(RoundErrorCode.ROUND_STATUS_EQUALS);
        }
    }

    public static boolean isAvailable(final RoundStatus status) {
        return status.equals(AVAILABLE);
    }
}
