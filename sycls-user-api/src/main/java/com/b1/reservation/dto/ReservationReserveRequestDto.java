package com.b1.reservation.dto;

import com.b1.constant.ReservationConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record ReservationReserveRequestDto(
        @NotNull(message = "공연 회차 정보를 입력해 주세요")
        Long roundId,
        @NotNull(message = "좌석 정보가 누락되었습니다.")
        @Size(max = ReservationConstant.MAXIMUM_SELECTED_SEAT, message = "최대 좌석 수를 초과하였습니다.")
        Set<Long> seatGradeIds
) {

}
