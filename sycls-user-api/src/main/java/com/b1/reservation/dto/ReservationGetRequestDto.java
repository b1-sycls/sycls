package com.b1.reservation.dto;

import jakarta.validation.constraints.NotNull;

public record ReservationGetRequestDto(
        @NotNull(message = "공연 회차 정보를 입력해 주세요")
        Long roundId
) {

}
