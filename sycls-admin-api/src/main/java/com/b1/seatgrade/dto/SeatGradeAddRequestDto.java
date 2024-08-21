package com.b1.seatgrade.dto;

import com.b1.seatgrade.entity.SeatGradeType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SeatGradeAddRequestDto(
        @NotNull(message = "회차 정보가 누락되었습니다.")
        Long roundId,

        @NotNull(message = "좌석 등급이 누락되었습니다.")
        SeatGradeType seatGradeType,

        @NotEmpty(message = "좌석이 누락되었습니다.")
        List<Long> seatIdList,

        @NotNull(message = "가격이 누락되었습니다.")
        @Max(value = 1000000000, message = "가격이 10억원을 넘을 수 없습니다.")
        @Min(value = 0, message = "가격이 0원보다 적을 수 없습니다.")
        Integer price
) {

}
