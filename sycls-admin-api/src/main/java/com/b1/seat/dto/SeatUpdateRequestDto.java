package com.b1.seat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SeatUpdateRequestDto(
        @NotNull(message = "공연장 정보가 누락되었습니다.")
        Long placeId,

        @NotBlank(message = "좌석이 누락되었습니다.")
        @Size(max = 10, message = "최대 10자까지 입력가능합니다.")
        String code
) {

}
