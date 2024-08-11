package com.b1.seat.entity;

import com.b1.exception.customexception.SeatAlreadyDeletedException;
import com.b1.exception.errorcode.SeatErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Seat Status")
@Getter
@RequiredArgsConstructor
public enum SeatStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;
    private final String value;

    /**
     * 좌석 삭제 상태 체크
     */
    public static void checkDeleted(SeatStatus status) {
        if (status.equals(DISABLE)) {
            log.error("삭제된 상태 | {}", status.value);
            throw new SeatAlreadyDeletedException(SeatErrorCode.ALREADY_DELETED_SEAT);
        }
    }
}
