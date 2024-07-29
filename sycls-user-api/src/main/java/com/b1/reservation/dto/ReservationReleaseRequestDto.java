package com.b1.reservation.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record ReservationReleaseRequestDto(
        @NotNull(message = "점유Id 정보가 누락되었습니다.")
        Set<Long> reservationIds
) {

}
