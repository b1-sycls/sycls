package com.b1.seatgrade.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record SeatGradeDeleteRequestDto(
        @NotEmpty(message = "삭제할 좌석이 누락됬습니다.")
        List<Long> seatGradeIdList

) {

}
