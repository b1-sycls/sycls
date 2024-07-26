package com.b1.seatgrade.dto;

import com.b1.seatgrade.entity.SeatGradeType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SeatGradeAddRequestDto(
        @NotNull(message = "공연 정보가 누락되었습니다.")
        Long contentId,

        @NotNull(message = "회차 정보가 누락되었습니다.")
        Long roundId,

        @NotNull(message = "좌석 등급이 누락되었습니다.")
        SeatGradeType seatGradeType,

        @NotEmpty(message = "좌석이 누락되었습니다.")
        List<Long> seatIdList,

        @NotNull(message = "가격이 누락되었습니다.")
        Integer price
) {

}
