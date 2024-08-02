package com.b1.seatgrade.dto;

import com.b1.seatgrade.entity.SeatGradeType;
import jakarta.validation.constraints.NotNull;

public record SeatGradeUpdateRequestDto(
        @NotNull(message = "회차 정보가 누락되었습니다.")
        Long roundId,

        @NotNull(message = "수정할 좌석이 누락되었습니다.")
        Long seatGradeId,

        @NotNull(message = "좌석의 등급이 누락되었습니다.")
        SeatGradeType seatGradeType,

        @NotNull(message = "가격이 누락되었습니다.")
        Integer price
) {

}
