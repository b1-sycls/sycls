package com.b1.seat.entity;

import com.b1.exception.customexception.SeatGradeAlreadySoldOutException;
import com.b1.exception.errorcode.SeatGradeErrorCode;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatGradeStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;
    private final String value;

    public static void checkEnable(Set<SeatGrade> seatGrades) {
        seatGrades.forEach(
                sg -> {
                    if (sg.getStatus().equals(DISABLE)) {
                        throw new SeatGradeAlreadySoldOutException(
                                SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
                    }
                });
    }

}