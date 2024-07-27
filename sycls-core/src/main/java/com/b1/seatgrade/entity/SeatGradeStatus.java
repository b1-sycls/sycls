package com.b1.seatgrade.entity;

import com.b1.exception.customexception.SeatGradeAlreadyDeletedException;
import com.b1.exception.customexception.SeatGradeAlreadySoldOutException;
import com.b1.exception.errorcode.SeatGradeErrorCode;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Seat Grade Status")
@Getter
@RequiredArgsConstructor
public enum SeatGradeStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    SOLD_OUT("SOLD_OUT"),
    ;

    private final String value;

    public static void checkEnable(final Set<SeatGrade> seatGrades) {
        seatGrades.forEach(
                sg -> {
                    if (!sg.getStatus().equals(ENABLE)) {
                        log.error("이미 매진된 좌석 | request {}", sg.getStatus());
                        throw new SeatGradeAlreadySoldOutException(
                                SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
                    }
                });
    }

    public static void checkDeleted(final SeatGradeStatus status) {
        if (status.equals(DISABLE)) {
            log.error("삭제된 상태. | {}", status);
            throw new SeatGradeAlreadyDeletedException(
                    SeatGradeErrorCode.SEAT_GRADE_ALREADY_DELETED);
        }
    }

    public static Boolean isEnableStatus(final SeatGradeStatus status) {
        return ENABLE.equals(status);
    }

}