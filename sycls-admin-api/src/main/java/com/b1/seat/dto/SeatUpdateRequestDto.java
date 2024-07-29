package com.b1.seat.dto;

import com.b1.seat.entity.SeatStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SeatUpdateRequestDto(
        @NotBlank(message = "좌석이 누락되었습니다.")
        @Size(max = 10, message = "최대 10자까지 입력가능합니다.")
        String code,

        @NotNull(message = "좌석 상태가 누락되었습니다.")
        SeatStatus status
) {

}
