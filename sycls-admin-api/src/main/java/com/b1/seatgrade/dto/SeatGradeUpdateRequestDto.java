package com.b1.seatgrade.dto;

import com.b1.seatgrade.entity.SeatGradeType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SeatGradeUpdateRequestDto(
        @NotEmpty(message = "수정할 좌석이 누락되었습니다.")
        List<Long> seatGradeIdList,

        @NotNull(message = "좌석의 등급이 누락되었습니다.")
        SeatGradeType seatGradeType,

        @NotNull(message = "가격이 누락되었습니다.")
        Integer price
) {

}
