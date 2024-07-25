package com.b1.content.entity;

import com.b1.exception.customexception.BookingNotAvailableException;
import com.b1.exception.errorcode.RoundErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoundStatus {
    WAITING("WAITING"),    // 대기
    AVAILABLE("AVAILABLE"),  // 예매 가능
    CLOSED("CLOSED"),  // 마감
    ;

    private final String value;

    public static void checkAvailable(RoundStatus status) {
        if (!status.equals(AVAILABLE)) {
            throw new BookingNotAvailableException(RoundErrorCode.BOOKING_NOT_AVAILABLE);
        }
    }
}
