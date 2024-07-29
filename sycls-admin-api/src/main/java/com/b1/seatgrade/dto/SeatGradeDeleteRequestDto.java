package com.b1.seatgrade.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SeatGradeDeleteRequestDto(
        @NotNull(message = "회차 정보가 누락되었습니다.")
        Long roundId,

        @NotEmpty(message = "삭제할 좌석이 누락됬습니다.")
        List<Long> seatGradeIdList

) {

}
